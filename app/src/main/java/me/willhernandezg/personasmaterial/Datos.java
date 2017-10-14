package me.willhernandezg.personasmaterial;

import java.util.ArrayList;

/**
 * Created by android on 07/10/2017.
 */

public class Datos {

    private static ArrayList<Persona> personas = new ArrayList();

    public static void guardarPersona(Persona p){
        personas.add(p);
    }

    public static void editarPersona(Persona p){
        personas.set(0,p);
    }

    public static ArrayList<Persona> obtenerPersonas(){
        return personas;
    }

    public static Boolean eliminarPersona(Persona p){
        for (int i = 0; i < personas.size() ; i++) {
            if (p.getCedula().equals(personas.get(i).getCedula())){
                personas.remove(i);
                return true;
            }
        }
        return false;
    }


}
