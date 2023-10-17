//import org.apache.commons.lang.SerializationUtils;
//import java.util.Base64;
//import java.io.Serializable;
//import src.samsung.pqc.PQCAdapter;
//import src.samsung.pqc.IPQCAdapter;
//import src.samsung.pqc.util.KeyPair;
//import src.samsung.pqc.util.Demo;
import pqcsignature.Sign;

public class TestSign {

//	public static byte[] fromJavaToByteArray(Serializable object) {
//	return SerializationUtils.serialize(object);
  //  }

//public static Object fromByteArrayToJava(byte[] bytes) {
//	return SerializationUtils.deserialize(bytes);/
//}

    public static void main(String[] args) {

	
	byte[] sk = null;
	byte[] pk = null;
	byte[] m = null;
	byte[] ct = null;
	byte[] s = null;
	byte[] input = null;
	byte[] output= null;
	byte[] outputTotal=null;
	//KeyPair keyPair = null;

	//IPQCAdapter iadapter = null;


	//String algorithm = args[0];

	//PQCAdapter adapter = new PQCAdapter();
	Sign sign = new Sign();
	//if (algorithm.equals("KYBER")) {
		pk = new byte[2592];
		sk = new byte[4880];
		s = new byte[4598];
		m = new byte[3];
		//iadapter = adapter.getAdapter(2);
		//keyPair = iadapter.crypto_kem_keypair(1);
		sign.crypto_sign_keypair(pk,sk);
	//} else if (algorithm.equals("SABER")){
 	//	ct = new byte[1088];
	//	iadapter = adapter.getAdapter(1);
	//	keyPair = iadapter.crypto_kem_keypair(2);
	//}
	String chavePublica = "";
	for(int i=0;i<pk.length;i++) {
		if (i==0) {
		chavePublica = pk[i]+"";
		}
		chavePublica = chavePublica+","+pk[i];
	}
	System.out.println("Chave Publica:" + chavePublica + "\n");

	for(int i=0;i<m.length;i++) {
		m[i] = 0;

	}

	System.out.println("Signature worked:" + sign.crypto_sign_sign(s, 4598, m, 3, sk, 4880) + "\n");

System.out.println("Signature size:" + s.length + "\n");

	System.out.println("Signature verification:" + sign.crypto_sign_verify(s, 4598, m, 3, pk, 2592) + "\n");


	/*pk = keyPair.getPk();
	sk = keyPair.getSk();
	String chavePublica = "";
	for(int i=0;i<pk.length;i++) {
		if (i==0) {
		chavePublica = pk[i]+"";
		}
		chavePublica = chavePublica+","+pk[i];
	}
	System.out.println("Chave Publica no " + algorithm + ": " + chavePublica + "\n");

	String chavePrivada="";
	for(int i=0;i<sk.length;i++) {
		if (i==0) {
		chavePrivada = sk[i]+"";
		}
		chavePrivada = chavePrivada+","+sk[i];
	}
	System.out.println("Chave Privada no " + algorithm + ": " + chavePrivada + "\n");


	Demo object = new Demo(1, "geeksforgeekss");
	byte[] encoded =           	Base64.getEncoder().encode(TestKemEncDec.fromJavaToByteArray(object));
	
	String objetoSerializadoString = "";
	for(int i=0;i<encoded.length;i++) {
		if (i==0) {
		objetoSerializadoString = encoded[i]+"";
		} else {
		objetoSerializadoString= objetoSerializadoString+","+encoded[i];
		}
	}
	System.out.println("objetoSerializadoInicial no " + algorithm + ":" +  objetoSerializadoString + "\n");
	//System.out.println("objetoSerializadoT:" + encoded.length + "\n");


	int qtBlocos = encoded.length/32;
	int k =0;
	int l =0;
	output = new byte[32];
	outputTotal= new byte[encoded.length];
	for(int j=0;j<qtBlocos;j++){
		input = new byte[32];
                l = 0;
		for(k=j*32;k<(j+1)*32;k++) {
		 	input[l]=encoded[k];
			//System.out.println("input:"+input[l]+"");
			l++;
		}
		iadapter.crypto_kem_enc(ct, input, pk);
		iadapter.crypto_kem_dec(output,ct,sk);
		l = 0;
		for(k=j*32;k<(j+1)*32;k++) {
		 	outputTotal[k]=output[l];
			//System.out.println("output:"+output[l]+"");
			l++;
		}
	}
	input = new byte[32];
	l = 0;
	for(int j=qtBlocos*32;j<((encoded.length/32)+(encoded.length%32));j++) {
		input[l]=encoded[j];
		l++;
	}
	iadapter.crypto_kem_enc(ct, input, pk);
	iadapter.crypto_kem_dec(output,ct,sk);
	l = 0;
	for(int j=qtBlocos*32;j<((encoded.length/32)+(encoded.length%32));j++) {
		outputTotal[j]=output[l];
		l++;
	}
	
	String objetoOriginal = "";
	for(int i=0;i<outputTotal.length;i++) {
		if (i==0) {
		objetoOriginal = outputTotal[i]+"";
		} else{
		objetoOriginal = objetoOriginal+","+outputTotal[i];
		}
	}
	System.out.println("objetoSerializadoOriginal no " + algorithm + ":" +  objetoOriginal + "\n");

	byte[] decoded = Base64.getDecoder().decode(outputTotal);
	object = (Demo)TestKemEncDec.fromByteArrayToJava(decoded);
	System.out.println("parametro a do objeto: " + object.a);
*/
    }



}
