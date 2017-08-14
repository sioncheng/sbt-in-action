import scala.sys.process.Process

name := "preowned-kittens"

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHa")

gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lineStream.head

val makeVersionProperties = taskKey[Seq[File]] ("Create a version.properties" +
    " file we can find at runtime.")

def KittenProject(name: String): Project = (
    Project(name, file(name)).
    settings(
        version := "1.0",
        organization := "com.preowened-kittens",
        libraryDependencies += "org.specs2" % "specs2-core_2.12" % "3.8.6" % "test"
    )
)




lazy val common = (
    KittenProject("common").
    settings(
        makeVersionProperties := {
                    val propFile = (resourceManaged in Compile).value / "version.properties"
                    val content = "version=%s".format(gitHeadCommitSha.value)
                    IO.write(propFile, content)
                    Seq(propFile)
                }

        )
)

lazy val analytics = (
    KittenProject("analytics").
    dependsOn(common).
    settings()
)

lazy val website = (
    KittenProject("website").
    dependsOn(common).
    settings()
)