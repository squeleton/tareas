import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    static ArrayList<String> tasks = new ArrayList<>();

    public ArrayList<String> getArray(){
        return tasks;
    }

    private static void cargarTareas() {
        try {
            File archivo = new File("tareas.txt");
            if (archivo.exists()) {
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNextLine()) {
                    String tarea = scanner.nextLine();
                    if (!tarea.isEmpty()) {
                        tasks.add(tarea);
                    }
                }
                scanner.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        cargarTareas();
        menu();
    }


    public static void menu(){

        System.out.println("Bienvenido Nad!");
        System.out.println("==========================");
        System.out.println("      Lista de Tareas ");
        System.out.println("==========================");

        System.out.println("1. Ver Tareas");
        System.out.println("2. Agregar Tareas");
        System.out.println("3. Salir");

        String option = inputString("Que deseas hacer: ");

        switch (option){
            case "1": menu1();
            case "2": addTask();
            case "3": salir();
            default: handleCase();
        }

    }

    private static void handleCase() {
        System.out.println("Nada que ver hermano ponte vio. Del 1 al cuatrsho");
        menu();
    }


    public static void menu1(){
        System.out.println("-----------------");
        System.out.println("  POR HACER HOY");
        System.out.println("-----------------");

        if (tasks.isEmpty()){
            System.out.println("!Sin tareas por ahora! 👌");
        }else{
            int id = 0;
            for(String t: tasks){
                int newNumber = id += 1;
                System.out.println(newNumber + "." + t);
            }
        }

        String completedTask = inputString("Escribe el nombre de la tarea completada o 'salir' para... salir: ");

        switch (completedTask){
            case "salir": menu();
            default: makeItComplete(completedTask);
            //Falta un caso para cuando falle el usuario.

        }



    }

    private static void makeItComplete(String completedTask) {
        // ArrayList<String> ejemplo = new ArrayList<>(Arrays.asList("1","2"));

       if(tasks.contains(completedTask)){
           tasks.remove(completedTask);
           System.out.println("Eliminado exitosamente!");
           try(FileWriter writer = new FileWriter("tareas.txt")) {
               for (String t : tasks){
                   try {
                       writer.write(t + "\n");
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   }
               }
           }catch (IOException e){
               e.printStackTrace();
           }
           menu1();
       }else{
           System.out.println("Esa tarea no existe. Intenta nuevamente");
           menu1();
       }

    }


    public static void addTask() {
        System.out.println("Tareas en la lista: ");
        System.out.println("-----------------");

        for(String t: tasks){
            System.out.println(t + ",");
        }
        System.out.println("-----------------");

        String newTask = inputString("Agregar: ");
        tasks.add(newTask);
        try(FileWriter writer = new FileWriter("tareas.txt")) {
            for (String t : tasks){
                try {
                    writer.write(t + "\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        redirect();

    }

    private static void redirect() {
        System.out.println("Agregado correctamente!");
        String option = inputString("'1' para agregar una nueva tarea / '0' para volver al menu: ");

        if(Objects.equals(option,"1")){
            addTask();
        }else if(Objects.equals(option,"0")){
            menu();
        } else{
            System.out.println("hmm. Intenta de nuevo...");
            redirect();
        }
    }

    private static void salir(){
        String Q = inputString("¿Estar seguro que deseas salir? (y/n)");

        if (Objects.equals(Q, "y")){
            System.out.println("Gracias por usar el programa hoy, adios!");
            System.exit(0);
        } else if (Objects.equals(Q,"n")){
            menu();
        } else{
            System.out.println("Lo ingresado no es valido, ingresa: 'y' para salir. 'n' para quedarte");
            salir(); // Función recursiva

        }

    }

    public static String inputString(String text){
        Scanner reader = new Scanner(System.in);
        System.out.printf(text);
        return reader.next();
    }
}
