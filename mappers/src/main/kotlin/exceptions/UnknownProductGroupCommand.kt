package exceptions

import models.ProductGroupCommand

class UnknownProductGroupCommand(command: ProductGroupCommand) : Throwable("Wrong command $command at mapping toTransport stage")
