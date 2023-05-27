package com.crowdproj.marketplace.product.group.repo.gremlin.mappers

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpPropertyId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_DESCRIPTION
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_ID
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_LOCK
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_NAME
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_OWNER_ID
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_PROPERTY_ID
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_TMP_RESULT
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_VISIBILITY
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.RESULT_SUCCESS
import com.crowdproj.marketplace.product.group.repo.gremlin.exceptions.WrongEnumException
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr
import models.PrgrpGroupLock
import models.PrgrpVisibility
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty

fun GraphTraversal<Vertex, Vertex>.addMkplPrgrp(prgrp: PrgrpGroup): GraphTraversal<Vertex, Vertex> =
    this
        .property(VertexProperty.Cardinality.single, FIELD_NAME, prgrp.name.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_DESCRIPTION, prgrp.description.takeIf { it.isNotBlank() })
        .property(
            VertexProperty.Cardinality.single,
            FIELD_LOCK,
            prgrp.lock.takeIf { it != PrgrpGroupLock.NONE }?.asString()
        )
        .property(
            VertexProperty.Cardinality.single,
            FIELD_OWNER_ID,
            prgrp.ownerId.asString().takeIf { it.isNotBlank() }) // здесь можно сделать ссылку на объект владельца
        .property(
            VertexProperty.Cardinality.single,
            FIELD_VISIBILITY,
            prgrp.visibility.takeIf { it != PrgrpVisibility.NONE }?.name
        )
        .property(
            VertexProperty.Cardinality.set,
            FIELD_PROPERTY_ID,
            prgrp.properties.filter { it != PrgrpPropertyId.NONE }.mapTo(mutableSetOf()) { it.asString() }
        )

fun GraphTraversal<Vertex, Vertex>.listMkplPrgrp(result: String = RESULT_SUCCESS): GraphTraversal<Vertex, MutableMap<String, Any>> =
    project<Any?>(
        FIELD_ID,
        FIELD_OWNER_ID,
        FIELD_LOCK,
        FIELD_NAME,
        FIELD_DESCRIPTION,
        FIELD_VISIBILITY,
        FIELD_PROPERTY_ID,
        FIELD_TMP_RESULT,
    )
        .by(gr.id<Vertex>())
        .by(FIELD_OWNER_ID)
        .by(FIELD_LOCK)
        .by(FIELD_NAME)
        .by(FIELD_DESCRIPTION)
        .by(FIELD_VISIBILITY)
        .by(FIELD_PROPERTY_ID)
        .by(gr.constant(result))

fun Map<String, Any?>.toPrgrpGroup(): PrgrpGroup = PrgrpGroup(
    id = (this[FIELD_ID] as? String)?.let { PrgrpGroupId(it) } ?: PrgrpGroupId.NONE,
    ownerId = (this[FIELD_OWNER_ID] as? String)?.let { PrgrpUserId(it) } ?: PrgrpUserId.NONE,
    lock = (this[FIELD_LOCK] as? String)?.let { PrgrpGroupLock(it) } ?: PrgrpGroupLock.NONE,
    name = (this[FIELD_NAME] as? String) ?: "",
    description = (this[FIELD_DESCRIPTION] as? String) ?: "",
    visibility = when (val value = this[FIELD_VISIBILITY]) {
        PrgrpVisibility.VISIBLE_PUBLIC.name -> PrgrpVisibility.VISIBLE_PUBLIC
        PrgrpVisibility.VISIBLE_TO_GROUP.name -> PrgrpVisibility.VISIBLE_TO_GROUP
        PrgrpVisibility.VISIBLE_TO_OWNER.name -> PrgrpVisibility.VISIBLE_TO_OWNER
        null -> PrgrpVisibility.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "visibility = '$value' cannot be converted to ${PrgrpVisibility::class}"
        )
    },
    properties = (this[FIELD_PROPERTY_ID] as? Set<String>)?.mapTo(mutableSetOf()) { PrgrpPropertyId(it) } ?: mutableSetOf(),
)