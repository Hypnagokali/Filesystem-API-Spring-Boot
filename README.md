# Filesystem API for Spring Boot Applications

## Installation
* Autowire the PathWrapper bean
* pathWrapper.getPathByName("some name") will return a Path
* if checkPaths=true, DefaultPathWrapper checks existence of paths.

## Configuring paths

* use "application.properties" or create "filesystem.properties"
* define new paths via "filesystem-api" property:
  * filesystem-api.paths.temp=/some/temp/path
  * filesystem-api.paths.upload=/some/upload/path
* checkPaths Property (default = false)
  * if true, the PathWrapper have to check, if paths exists, or throw an Exception.

## Using the API

* ToDo:
  * copy and move
  * delete
  * get file as ByteArrayResource