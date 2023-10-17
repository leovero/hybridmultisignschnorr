package pqcsignature;

public class Sign {
    static { System.loadLibrary("pqcsign"); }

    public static native int crypto_sign_keypair(byte[] pk, byte[] sk);
    public static native int crypto_sign_sign(byte[] s, int slen, byte[] m, int mlen, byte[] sk, int sklen);
    public static native int crypto_sign_verify(byte[] s, int slen, byte[] m, int mlen, byte[] pk, int pklen);
}
