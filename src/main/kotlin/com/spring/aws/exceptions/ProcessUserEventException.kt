package com.spring.aws.exceptions

import java.lang.RuntimeException

class ProcessUserEventException(message: String) : RuntimeException(message)