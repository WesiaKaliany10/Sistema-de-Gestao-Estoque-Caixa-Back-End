package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Criptografia {

    public static String gerarHash(String senha) {
        String saltBase64 = geradorSaltBase64(senha.length());

        int somaDosBytesDoSalt = 0;

        for (byte b : saltBase64.getBytes()) {
            somaDosBytesDoSalt += (b & 0xff);
        }

        int deslocamento = (senha.length() + somaDosBytesDoSalt) % 94;

        StringBuilder resultado = new StringBuilder();

        int saida  = 0;
        for (char c : senha.toCharArray()) {
            int codigo = (int) c;

            if(codigo >= 33 && codigo <= 126) {

                int novoCodigo = 33 + ((codigo - 33 + deslocamento) % 94);

                if(novoCodigo < 33){
                    novoCodigo += 94;
                }
                resultado.append(novoCodigo);
            }
        }
        String converte = resultado.toString();

        String[]somando = converte.split("");

        for (String s : somando) {
            int soma = 0;
            soma += (int) s.charAt(0);
            saida += soma;

        }

        saida = saida % 997;

        String hash = String.valueOf(saida + "$" + saltBase64);

        return hash;
    }

    public static byte[] geradorSaltBytes(int tamanho){
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            sr = new SecureRandom();
        }
        byte[] salt = new byte[tamanho];
        sr.nextBytes(salt);
        return salt;
    }

    public static String geradorSaltBase64(int tamanho){
        byte[] salt = geradorSaltBytes(tamanho);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static boolean verificarSenha(String senha, String hashSalvo){
        String[] partes = hashSalvo.split("\\$");
        int hashOrigem = Integer.parseInt(partes[0]);
        String salt = partes[1];

        int somaDosBytesDoSalt = 0;

        for (byte b : salt.getBytes()) {
            somaDosBytesDoSalt += (b & 0xff);
        }

        int deslocamento = (senha.length() + somaDosBytesDoSalt) % 94;
        StringBuilder resultado = new StringBuilder();
        int saida  = 0;
        for (char c : senha.toCharArray()) {
            int codigo = (int) c;

            if(codigo >= 33 && codigo <= 126) {

                int novoCodigo = 33 + ((codigo - 33 + deslocamento) % 94);

                if(novoCodigo < 33){
                    novoCodigo += 94;
                }
                resultado.append(novoCodigo);
            }
            String converte = resultado.toString();

            String[]somando = converte.split("");

            for (String s : somando) {
                int soma = 0;
                soma += (int) s.charAt(0);
                saida += soma;

            }
        }

        return saida == hashOrigem;

    }

}
