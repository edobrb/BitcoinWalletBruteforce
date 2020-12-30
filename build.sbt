name := "BitcoinWalletBruteforce"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies += "fr.acinq" %% "bitcoin-lib" % "0.18"

libraryDependencies += "org.bitcoinj" % "bitcoinj-core" % "0.15.9"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.2"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
