package com.jeferro.products.infrastructure.shared.utils.component_scan

import com.jeferro.lib.domain.events.EventHandler
import com.jeferro.lib.domain.handlers.Handler
import com.jeferro.lib.domain.services.DomainService
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