package com.example.organize;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class OrgaAI {
    private static final String TAG = "OrgaAI";
    private final DatabaseHelper databaseHelper;

    public OrgaAI(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    /**
     * Calcula o IMC com base nos dados do usuário
     * @return valor do IMC ou 0 se os dados forem inválidos
     */
    public double calcularValorIMC() {
        try (Cursor cursor = databaseHelper.getUsuario()) {
            if (cursor != null && cursor.moveToFirst()) {
                double peso = getDoubleFromCursor(cursor, "peso");
                double altura = getDoubleFromCursor(cursor, "altura");

                if (isDadosInvalidos(peso, altura)) {
                    Log.w(TAG, "Dados inválidos para cálculo do IMC");
                    return 0;
                }

                return calcularIMC(peso, altura);
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao calcular IMC: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Método auxiliar para cálculo do IMC com parâmetros explícitos
     */
    private double calcularIMC(double peso, double altura) {
        double alturaMetros = altura / 100; // Converte cm para metros
        return peso / (alturaMetros * alturaMetros);
    }

    /**
     * Gera análise completa da saúde do usuário
     */
    public String gerarAnaliseSaude() {
        try (Cursor cursor = databaseHelper.getUsuario()) {
            if (cursor != null && cursor.moveToFirst()) {
                String nome = getStringFromCursor(cursor, "nome");
                double peso = getDoubleFromCursor(cursor, "peso");
                double altura = getDoubleFromCursor(cursor, "altura");
                String horarioSono = getHorarioSonoFormatado(cursor);

                double imc = calcularIMC(peso, altura); // Usando o método com parâmetros

                return formatarAnaliseCompleta(nome, peso, altura, horarioSono, imc);
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao gerar análise: " + e.getMessage());
        }
        return getMensagemDadosIncompletos();
    }

    // Métodos auxiliares abaixo...

    private boolean isDadosInvalidos(double peso, double altura) {
        return peso <= 0 || altura <= 0;
    }

    private double getDoubleFromCursor(Cursor cursor, String coluna) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(coluna));
    }

    private String getStringFromCursor(Cursor cursor, String coluna) {
        return cursor.getString(cursor.getColumnIndexOrThrow(coluna));
    }

    private String getHorarioSonoFormatado(Cursor cursor) {
        return getStringFromCursor(cursor, "horario_dormir") + " - " +
                getStringFromCursor(cursor, "horario_acordar");
    }

    private String formatarAnaliseCompleta(String nome, double peso, double altura,
                                           String horarioSono, double imc) {
        return String.format(
                "👋 Olá, %s!\n\n" +
                        "📊 Seu IMC: %.1f\n" +
                        "⚖️ Peso: %.1f kg\n" +
                        "📏 Altura: %.0f cm\n" +
                        "💤 Sono: %s\n\n" +
                        "%s",
                nome, imc, peso, altura, horarioSono, getRecomendacaoIMC(imc)
        );
    }

    private String getRecomendacaoIMC(double imc) {
        if (imc == 0) return "❌ Dados insuficientes para cálculo";

        String[] categorias = {
                "Abaixo do peso (IMC < 18.5)",
                "Peso saudável (IMC 18.5-24.9)",
                "Sobrepeso (IMC 25-29.9)",
                "Obesidade (IMC ≥ 30)"
        };

        String[] recomendacoes = {
                "• Aumentar consumo calórico saudável\n• Musculação 3x/semana",
                "• Manter rotina de exercícios\n• Dieta balanceada",
                "• 150min exercícios/semana\n• Reduzir açúcares",
                "• Acompanhamento médico\n• Atividade física diária"
        };

        int indice = imc < 18.5 ? 0 :
                imc < 25 ? 1 :
                        imc < 30 ? 2 : 3;

        return String.format(
                "🏷️ %s\n\n💡 Recomendações:\n%s\n\nℹ️ IMC ideal: 18.5 - 24.9",
                categorias[indice], recomendacoes[indice]
        );
    }

    private String getMensagemDadosIncompletos() {
        return "🔍 Complete seus dados nas configurações para obter uma análise.";
    }
}
