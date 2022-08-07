# Filesystem API for Spring Boot Applications

## Quickstart
* build jar and include it into a Spring Boot project
* Autowire the PathWrapper bean to use PathWrapper
* Autowire the FilesystemApi bean to use the API
  * PathWrapper
    * pathWrapper.getPathByName("some name") will return a Path to a specific location
  * FilesystemApi
    * uploadFile(Multipartfile file) will return a FsFile
    * FsFile can be implemented by an JPA Entity (e.g.: "Upload")

## Configuring paths

* use "application.properties" or create "filesystem.properties"
* define new paths via "filesystem-api" property:
  * filesystem-api.paths.temp=/some/temp/path
  * filesystem-api.paths.upload=/some/upload/path
* checkPaths Property (default = false)
  * if true, the PathWrapper have to check if the paths exists or throw an Exception.

## UniqueFilename
* UniqueFilenameGenerator
  * Config: UniqueFilenameIndexGenerator or UniqueFilenameDateTimeGenerator
  * Build Customer Generator (ToDo)

## Using the API


### Uploading a file


* ToDo:
  * copy and move
  * delete
  * get file as ByteArrayResource
