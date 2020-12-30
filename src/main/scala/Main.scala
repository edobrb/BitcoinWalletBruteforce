import java.io.FileWriter
import java.security.SecureRandom

import fr.acinq.bitcoin._
import fr.acinq.bitcoin.Crypto._
import scodec.bits._

import scala.collection.immutable.HashSet
import scala.io.Source
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Address
import org.bitcoinj.params.MainNetParams

import scala.util.{Failure, Success, Try}


//uncompressed WIF starts with "5"
//compressed WIF starts with "L" or "K"
//TODO: im currently using two bitcoin libraries (org.bitcoinj and fr.acinq.bitcoin)
//because fr.acinq.bitcoin seems that the support for uncompressed WIF is not available and
//with org.bitcoinj i've not found a way to print the compressed WIF in base58 formats
//so i use a mix of both, the performance was not diminished

object Main extends App {

  val mainNet = MainNetParams.get
  val random = new SecureRandom()

  val wallets: Set[ByteVector] = Try {
    val filename = "wallets.txt"
    val source = Source.fromFile(filename)
    val wallets = HashSet.from(source.getLines.map(address => {
      Try {
        val (_, addressHash160) = Base58Check.decode(address)
        addressHash160
      }
    }).collect({
      case Success(address) => address
    }).toSeq)
    source.close()
    wallets
  } match {
    case Success(wallets) => wallets
    case Failure(_) =>
      println("wallets.txt not loaded.")
      System.exit(-1)
      Set()
  }

  println(s"Loaded ${wallets.size} wallets")

  Iterator.continually(random.generateSeed(32)).foldLeft((0.toLong, 0.toLong, System.currentTimeMillis() - 1000))({
    case ((c, i, lastLogMillis), seed) =>

      //Check compressed WIF
      val k1: ECKey = ECKey.fromPrivate(seed, true)
      val compressedAddress = Address.fromKey(mainNet, k1, org.bitcoinj.script.Script.ScriptType.P2PKH)
      if (wallets.contains(ByteVector(compressedAddress.getHash))) {
        val secretC = PrivateKey(ByteVector(seed)).toBase58(Base58.Prefix.SecretKey)
        writeWalletInfo(seed, compressedAddress.toString, secretC)
        println(s"Found compressed WIF! $compressedAddress $secretC ${seed.toSeq}")
        System.exit(0)
      }

      //Check uncompressed WIF
      val k2: ECKey = ECKey.fromPrivate(seed, false)
      val uncompressedAddress = Address.fromKey(mainNet, k2, org.bitcoinj.script.Script.ScriptType.P2PKH)
      if (wallets.contains(ByteVector(uncompressedAddress.getHash))) {
        val secretU = Base58Check.encode(Base58.Prefix.SecretKey, ByteVector(k2.getPrivKeyBytes))
        writeWalletInfo(seed, uncompressedAddress.toString, secretU)
        println(s"Found uncompressed WIF! $uncompressedAddress $secretU ${seed.toSeq}")
        System.exit(0)
      }

      //Print stats every second
      val dt = System.currentTimeMillis() - lastLogMillis
      if (dt > 1000) {
        println(s"Generated $i wallets ($c Wallets/s)")
        (0, i + 1, System.currentTimeMillis())
      } else {
        (c + 1, i + 1, lastLogMillis)
      }
  })


  //relax, this code will never be executed
  def writeWalletInfo(seed: Array[Byte], address: String, secret: String): Unit = {
    val fw = new FileWriter("found.txt", true)
    try {
      fw.write("SEED: " + seed.map("%02X" format _).mkString + "\n")
      fw.write("ADDRESS: " + address + "\n")
      fw.write("SECRET: " + secret + "\n")
      fw.write("SEED (scala seq): " + seed.toSeq + "\n")
    }
    finally fw.close()
  }
}
