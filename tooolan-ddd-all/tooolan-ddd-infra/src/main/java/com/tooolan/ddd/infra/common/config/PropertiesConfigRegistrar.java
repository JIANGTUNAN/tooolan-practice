package com.tooolan.ddd.infra.common.config;

import com.tooolan.ddd.domain.common.annotation.PropertiesConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Objects;
import java.util.Set;

/**
 * DDD 配置 Bean 注册器
 * 扫描 domain 层标注 @DddConfig 的配置类，自动绑定配置值并注册为 Spring Bean
 *
 * @author tooolan
 * @since 2026年3月3日
 */
@Slf4j
public class PropertiesConfigRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * Spring 环境对象，用于读取配置文件
     */
    private Environment environment;

    /**
     * 设置 Spring 环境对象
     * Spring 容器启动时会自动调用此方法，注入 Environment 对象
     *
     * @param environment Spring 环境对象，不能为 null
     */
    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    /**
     * 注册配置 Bean
     * 扫描 domain 层的 @DddConfig 注解类，绑定配置值并注册为 Spring Bean
     *
     * @param importingClassMetadata 导入类的元数据（未使用）
     * @param registry               Bean 定义注册表，不能为 null
     * @throws RuntimeException 当配置类加载失败或实例化失败时抛出
     */
    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        // 1. 创建扫描器，只扫描带 @DddConfig 的类
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(PropertiesConfig.class));

        // 2. 扫描 domain 层包路径
        String basePackage = "com.tooolan.ddd.domain";
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);

        // 3. 遍历扫描到的配置类
        for (BeanDefinition candidate : candidateComponents) {
            try {
                String beanClassName = candidate.getBeanClassName();
                Class<?> configClass = ClassUtils.forName(
                        Objects.requireNonNull(beanClassName),
                        this.getClass().getClassLoader()
                );

                // 4. 获取注解上的 prefix 值
                PropertiesConfig annotation = configClass.getAnnotation(PropertiesConfig.class);
                if (annotation == null) {
                    continue;
                }

                String prefix = annotation.prefix();

                // 5. 使用 Spring Boot 的 Binder 将 YAML 中的配置绑定到这个类上
                BindResult<?> bindResult = Binder.get(environment).bind(prefix, configClass);

                Object configInstance;
                if (bindResult.isBound()) {
                    // 如果 yml 里有配置，直接取出绑定好的对象
                    configInstance = bindResult.get();
                } else {
                    // 如果 yml 里完全没有配这个前缀，给个默认空对象
                    log.warn("未找到配置前缀: {}，使用默认空实例", prefix);
                    try {
                        configInstance = configClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(
                                "实例化配置类失败，请确保类有无参构造函数: " + configClass.getName(),
                                e
                        );
                    }
                }

                // 6. 将绑定好数据的实例注册到 Spring 容器中
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(configClass);
                // 使用 Supplier 直接返回我们刚才绑定好的实例
                beanDefinition.setInstanceSupplier(() -> configInstance);

                // 注册 Bean，Bean 的名字就用类名首字母小写
                String beanName = ClassUtils.getShortNameAsProperty(configClass);
                registry.registerBeanDefinition(beanName, beanDefinition);

            } catch (ClassNotFoundException e) {
                log.error("无法加载配置类", e);
                throw new RuntimeException("无法加载配置类", e);
            }
        }
    }

}
