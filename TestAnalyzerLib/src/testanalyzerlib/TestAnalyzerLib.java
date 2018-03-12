/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testanalyzerlib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Biblioteka odpowiedzialna za przetwarzanie danych pobranych przez aplikację.
 * Generuje statystyki, oblicza oceny i tworzy histogramy
 *
 * @author Luk
 */
public class TestAnalyzerLib {

    int tasksAmount = 0;
    int studentsAmount = 0;
    int[][] answersMatrix;
    int optionsAmount;
    List<Integer> correctAnswers;
    List<Integer> studentAnswers;
    List<Double> gradesHist;
    List<Integer> correctAnswersHist;

    /**
     * Metoda wczytująca klucz poprawnych odpowiedzi z aplikacji do biblioteki.
     *
     * @param correctAnswers tablica poprawnych odpowiedzi
     *
     */
    public void loadCorrectAnswersList(List<Integer> correctAnswers) {
        this.correctAnswers = correctAnswers;
        tasksAmount = correctAnswers.size();
    }

    /**
     * Metoda wczytująca listę odpowiedzi z aplikacji do biblioteki.
     *
     * @param studentAnswers tablica poprawnych odpowiedzi
     *
     */
    public void loadStudentAnswersList(List<Integer> studentAnswers) {
        this.studentAnswers = studentAnswers;
        studentsAmount = studentAnswers.size() / (tasksAmount + 1);
        answersListToTable();
    }

    /**
     * Metoda konwerująca listę wczytanych odpowiedzi do macierzy.
     *
     *
     *
     */
    public void answersListToTable() {
        answersMatrix = new int[studentsAmount][tasksAmount];
        Iterator<Integer> itr = studentAnswers.iterator();
        int getItr;
        for (int i = 0; i < studentsAmount; i++) {
            for (int k = 0; k < tasksAmount; k++) {
                getItr = itr.next();
                if (getItr < 500) {
                    answersMatrix[i][k] = getItr;
                } else {
                    getItr = itr.next();
                    if (getItr < 500) {
                        answersMatrix[i][k] = getItr;
                    }
                }
            }
        }
        // printAnswersListToTable();
    }

    /**
     * Metoda drukująca zawartość macierzy odpowiedzi studentów.
     *
     *
     *
     */
    public void printAnswersListToTable() {

        for (int i = 0; i < studentsAmount; i++) {
            for (int k = 0; k < tasksAmount; k++) {
                System.out.print(answersMatrix[i][k]);
                System.out.print(",");
            }
            System.out.print("\n");
        }
        System.out.println(studentAnswers.toString());
    }

    /**
     * Metoda licząca ile poprawnych odpwiedzi udzielono na karcie odpowiedzi.
     *
     * @param id wiersz ocen z macierzy punktów
     * @return liczba poprawnych odpowiedzi
     */
    public double countCorrectAnswers(int id) {
        double score = 0;

        for (int i = 0; i < tasksAmount; i++) {
            if (Objects.equals(answersMatrix[id][i], correctAnswers.get(i))) {
                score++;
            }
        }

        return score;
    }

    /**
     * Metoda wyliczająca ocenę na podstawie zdobytych punktów.
     *
     * @return ocena
     */
    public double gradeCounter() {
        double grade = 2;
        double percent;
        gradesHist = new ArrayList<>();
        for (int i = 0; i < studentsAmount; i++) {
            percent = (countCorrectAnswers(i) / tasksAmount) * 100;

            if (percent < 50) {
                grade = 2;
            } else if (percent >= 50 && percent < 60) {
                grade = 3;
            } else if (percent >= 60 && percent < 70) {
                grade = 3.5;
            } else if (percent >= 70 && percent < 80) {
                grade = 4;
            } else if (percent >= 80 && percent < 90) {
                grade = 4.5;
            } else if (percent >= 90 && percent < 100) {
                grade = 5;
            } else if (percent == 100) {
                grade = 5.5;
            }
            gradesHist.add(grade);
        }
        return grade;
    }

    /**
     * Metoda generująca histogram ocen.
     *
     * @return grafika histogramu
     */
    public ChartPanel gradesHistogram() {
        gradeCounter();
        Integer[] gradesAmount = {0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < gradesHist.size(); i++) {
            if (gradesHist.get(i) == 2) {
                gradesAmount[0]++;
            } else if (gradesHist.get(i) == 3) {
                gradesAmount[1]++;
            } else if (gradesHist.get(i) == 3.5) {
                gradesAmount[2]++;
            } else if (gradesHist.get(i) == 4) {
                gradesAmount[3]++;
            } else if (gradesHist.get(i) == 4.5) {
                gradesAmount[4]++;
            } else if (gradesHist.get(i) == 5) {
                gradesAmount[5]++;
            } else if (gradesHist.get(i) == 5.5) {
                gradesAmount[6]++;
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(gradesAmount[0], "", "2");
        dataset.addValue(gradesAmount[1], "", "3");
        dataset.addValue(gradesAmount[2], "", "3.5");
        dataset.addValue(gradesAmount[3], "", "4");
        dataset.addValue(gradesAmount[4], "", "4.5");
        dataset.addValue(gradesAmount[5], "", "5");
        dataset.addValue(gradesAmount[6], "", "5.5");

        JFreeChart chart = ChartFactory.createBarChart(
                "Rozkład ocen", //Chart Title
                "Skala ocen", // Category axis
                "Liczba ocen", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel CP = new ChartPanel(chart);
        return CP;
    }

    /**
     * Metoda generująca histogram poprawnych odpowiedzi.
     *
     * @return grafika histogramu
     */
    public ChartPanel correctAnswersHistogram() {

        int currVal;
        correctAnswersHist = new ArrayList<>();

        for (int i = 0; i < tasksAmount; i++) {
            correctAnswersHist.add(0);
        }

        for (int j = 0; j < studentsAmount; j++) {
            for (int i = 0; i < tasksAmount; i++) {
                if (Objects.equals(answersMatrix[j][i], correctAnswers.get(i))) {
                    currVal = correctAnswersHist.get(i);
                    currVal++;
                    correctAnswersHist.set(i, currVal);
                }
            }

        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < tasksAmount; i++) {
            dataset.addValue(correctAnswersHist.get(i), "", Integer.toString(i + 1));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Rozkład poprawnych odpowiedzi na dane pytanie", //Chart Title
                "Numer zadania", // Category axis
                "Liczba poprawnych odpowiedzi", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel CP = new ChartPanel(chart);
        return CP;
    }

    /**
     * Metoda generująca histogram odpowiedzi.
     *
     * @return grafika histogramu
     */
    public ChartPanel answersHistogram() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < optionsAmount - 1; i++) {
            dataset.addValue(Collections.frequency(studentAnswers, i + 1), "", Integer.toString(i + 1));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Rozkład wybranych numerów odpowiedzi w pytaniach", //Chart Title
                "Numer odpowiedzi", // Category axis
                "Liczba zaznaczeń", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel CP = new ChartPanel(chart);
        return CP;
    }

    /**
     * Metoda przekazująca listę indeksów studentów z ocenami
     *
     * @return lista indeks - ocena
     */
    public String printGrades() {

        String histogram = "";
        StringBuilder sB = new StringBuilder(histogram);
        int indexIdx = 0;
        for (int i = 0; i < studentsAmount; i++) {
            sB.append(studentAnswers.get(i + indexIdx));
            sB.append("\t");
            sB.append(gradesHist.get(i));
            sB.append("\n");
            indexIdx = indexIdx + tasksAmount;
        }

        histogram = sB.toString();
        return histogram;
    }

    public void loadAnswersSize(int maxOptionsAmount) {
        this.optionsAmount = maxOptionsAmount;
    }
}
