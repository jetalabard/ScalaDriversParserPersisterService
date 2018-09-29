package com.m2iformation.persister.support

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import resource.managed

trait SerializationSupport {

  def serialize[T](xs: Seq[T], fileOutput: String): Unit = {
    for {
      fos <- managed(new FileOutputStream(fileOutput))
      oos <- managed(new ObjectOutputStream(fos))
    } {
      oos.writeObject(xs)
    }
  }

  def deserialize[T](inputFile: String): Seq[T] = {
    managed(new FileInputStream(inputFile)) acquireAndGet { fis =>
      managed(new ObjectInputStream(fis)) acquireAndGet { ois =>
        ois.readObject.asInstanceOf[Seq[T]]
      }
    }
  }

}
