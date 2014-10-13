
package com.msevgi.dictionary.model;

import java.io.Serializable;

public class Words implements Serializable {
   private static final long serialVersionUID = 1L;

   private int               id;

   private String            mTurkishWord;
   private String            mEnglishWord;

   public Words() {
      super();
   }

   public Words(int id, String turkish_word, String english_word) {
      this.id = id;
      this.mTurkishWord = turkish_word;
      this.mEnglishWord = english_word;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getId() {
      return id;
   }

   public String getTurkish_word() {
      return mTurkishWord;
   }

   public void setTurkish_word(String turkish_word) {
      this.mTurkishWord = turkish_word;
   }

   public String getEnglish_word() {
      return mEnglishWord;
   }

   public void setEnglish_word(String english_word) {
      this.mEnglishWord = english_word;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Words{")
             .append(" id = ")
             .append(id)
             .append(", turkish_word = ")
             .append(mTurkishWord)
             .append(", english_word = ")
             .append(mEnglishWord)
             .append(" }");
      return builder.toString();
   }
}
