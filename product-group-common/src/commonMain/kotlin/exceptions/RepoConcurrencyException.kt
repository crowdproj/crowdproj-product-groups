package exceptions

import models.PrgrpGroupLock

class RepoConcurrencyException(
    expectedLock: PrgrpGroupLock,
    actualLock: PrgrpGroupLock?,
) : RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)