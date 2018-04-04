import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Crunchify.com
 *
 */

public class CrunchifyFindClassesFromJar {

    @SuppressWarnings("resource")
    public static JSONObject getCrunchifyClassNamesFromJar(String crunchifyJarName) {
        JSONObject crunchifyObject = new JSONObject();
        ArrayList<String> classNameList = new ArrayList();

        try {
            JarInputStream crunchifyJarFile = new JarInputStream(new FileInputStream(crunchifyJarName));
            JarEntry crunchifyJar;

            while (true) {
                crunchifyJar = crunchifyJarFile.getNextJarEntry();
                if (crunchifyJar == null) {
                    break;
                }
                if ((crunchifyJar.getName().endsWith(".class"))) {
                    String className = crunchifyJar.getName().replaceAll("/", "\\.");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    if(!myClass.endsWith("$")) classNameList.add(myClass);
                }
            }

            Collections.sort(classNameList, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

            JSONArray listofClasses = new JSONArray(classNameList);

            crunchifyObject.put("Jar File Name", crunchifyJarName);
            crunchifyObject.put("List of Class", listofClasses);
        } catch (Exception e) {
            System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
        }
        return crunchifyObject;
    }

    public static void main(String[] args) {

        JSONObject myList = getCrunchifyClassNamesFromJar("/Users/localhome/workdev/projectlocker/target/scala-2.12/projectlocker_2.12-1.0.jar");
        System.out.println(myList);
    }
}