package br.ifrs.edu.br.basesmanager.useful;

public class CriptografiaUtil {


	public static String encriptar(String x) {
		int chave = 5;
		String msgCript = "";
		for (int i = 0; i < x.length(); i++) {
			msgCript += (char) (x.charAt(i) + chave);
		}
		return msgCript;
	}

	public static String desencriptar(String msgCript) {
		int chave = 5;
		String msg = "";
		for (int i = 0; i < msgCript.length(); i++) {
			msg += (char) (msgCript.charAt(i) - chave);
		}
		return msg;
	}}
