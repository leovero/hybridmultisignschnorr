
#include "pqcsignature_Sign.h"
#include <stdio.h>
#include <openssl/evp.h>
#include <unistd.h>
#include <stdlib.h>
#include "sign.h"

#define KYBER_SYMBYTES 32
#define hash_h(OUT, IN, INBYTES) sha256(OUT, IN, INBYTES)
#define hash_g(OUT, IN, INBYTES) sha512(OUT, IN, INBYTES)

JNIEXPORT jint JNICALL Java_pqcsignature_Sign_crypto_1sign_1keypair	
  (JNIEnv *env, jclass cls, jbyteArray array1, jbyteArray array2){

unsigned char       pk[2592], sk[4880];

crypto_sign_keypair(pk,sk);

jbyte* buf1 = (jbyte*)calloc(sizeof(jbyte), 2592);
for(int i=0; i <2592; i++){
 buf1[i] =  pk[i];
}

jbyte* buf2 = (jbyte*)calloc(sizeof(jbyte), 4880);
for(int i=0; i <4880; i++){
 buf2[i] =  sk[i];
}

(*env)->SetByteArrayRegion(env, array1, 0, 2592, buf1); 
(*env)->SetByteArrayRegion(env, array2, 0, 4880, buf2); 

}

JNIEXPORT jint JNICALL Java_pqcsignature_Sign_crypto_1sign_1sign
  (JNIEnv *env, jclass cls, jbyteArray st, jint stlen, jbyteArray m, jint mlen, jbyteArray sk, jint sklen)
{

	unsigned char input[mlen];
	unsigned char skey[sklen];
	unsigned char s[stlen];
	
	int mlen_int = (int)mlen;
	int sklen_int = (int)sklen;

	size_t smlen_int;

	jbyte* bufinput  = (jbyte*)calloc(sizeof(jbyte), mlen_int);
	(*env)->GetByteArrayRegion(env, m, 0, mlen_int, bufinput);
	for(int i=0; i <mlen_int; i++){
 		input[i] =  bufinput[i];
	}

	jbyte* bufsk  = (jbyte*)calloc(sizeof(jbyte), sklen_int);
	(*env)->GetByteArrayRegion(env, sk, 0, sklen_int, bufsk);

	for(int i=0; i <sklen; i++){
 		skey[i] =  bufsk[i];
		
	}

        int result = crypto_sign(s, &smlen_int, input, mlen_int, skey);


	stlen = (int)smlen_int;
	jbyte* bufst = (jbyte*)calloc(sizeof(jbyte), stlen);
	for(int i=0; i <stlen; i++){
 		bufst[i] =  s[i];
 		
	}

	(*env)->SetByteArrayRegion(env, st, 0, stlen, bufst); 
	

	return stlen;
}

JNIEXPORT jint JNICALL Java_pqcsignature_Sign_crypto_1sign_1verify
  (JNIEnv *env, jclass cls, jbyteArray st, jint stlen, jbyteArray mt, jint mtlen, jbyteArray pk, jint pklen){

	unsigned char s[stlen];
	unsigned char pkey[pklen];
	unsigned char input[mtlen];

	jbyte* bufst  = (jbyte*)calloc(sizeof(jbyte), stlen);
	(*env)->GetByteArrayRegion(env, st, 0, stlen, bufst);

	for(int i=0; i <stlen; i++){
	  s[i] =  bufst[i];
	}

	jbyte* bufpk  = (jbyte*)calloc(sizeof(jbyte), pklen);
	(*env)->GetByteArrayRegion(env, pk, 0, pklen, bufpk);

	for(int i=0; i <pklen; i++){
	  pkey[i] =  bufpk[i];
	}

	size_t mtlen_int;

	jbyte* bufmt  = (jbyte*)calloc(sizeof(jbyte), mtlen);
	(*env)->GetByteArrayRegion(env, mt, 0, mtlen, bufmt);

	for(int i=0; i <mtlen; i++){
	  input[i] =  bufmt[i];
	}

	return crypto_sign_open(input, &mtlen_int, s, stlen, pkey);

	
}
