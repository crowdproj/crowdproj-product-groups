package com.crowdproj.marketplace.product.group.mapper.exceptions

import kotlin.reflect.KClass

class UnknownRequestClass(clazz: KClass<*>) : RuntimeException("Class $clazz cannot be mapped to MkplContext")
