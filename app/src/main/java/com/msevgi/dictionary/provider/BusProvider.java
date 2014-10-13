
package com.msevgi.dictionary.provider;

import com.squareup.otto.Bus;

public class BusProvider {
   private static Bus mBus = new Bus();

   public static Bus getInstance() {
      return mBus;
   }
}
