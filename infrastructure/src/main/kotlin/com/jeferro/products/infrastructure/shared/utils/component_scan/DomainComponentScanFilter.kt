package com.jeferro.products.infrastructure.shared.utils.component_scan

import com.jeferro.products.domain.shared.events.handlers.EventHandler
import com.jeferro.products.domain.shared.handlers.Handler
import com.jeferro.products.domain.shared.services.DomainService
import org.springframework.core.type.classreading.MetadataReader
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.core.type.filter.TypeFilter
import java.io.IOException


class DomainComponentScanFilter : TypeFilter {

    private val domainAnnotations: List<Class<out Annotation>> = listOf(
        DomainService::class.java,
        Handler::class.java,
        EventHandler::class.java
    )

    @Throws(IOException::class)
    override fun match(
        classMetadataReader: MetadataReader,
        metadataReaderFactory: MetadataReaderFactory
    ): Boolean {
        val classAnnotations = classMetadataReader.annotationMetadata.annotations
            .map { classAnnotation -> classAnnotation.type }

        return domainAnnotations
            .any { domainAnnotation -> classAnnotations.contains(domainAnnotation) }
    }
}