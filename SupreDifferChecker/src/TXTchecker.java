import java.io.*;
import java.util.*;
import java.util.Vector;

public class TXTchecker {

    public TXTchecker(String m_pathSrc, String m_pathCmp) {
        pathSrc = m_pathSrc;
        pathCmp = m_pathCmp;
    }

    public TXTchecker()
    {

    }

    public void setPathCmp(String pathCmp) {
        this.pathCmp = pathCmp;
    }

    public void setPathSrc(String pathSrc) {
        this.pathSrc = pathSrc;
    }

    private String text = "";
    private int v1Index = 0;
    private int v2Index = 0;
    private boolean flag_add = true;
    private boolean flag_delete = true;
    private String pathSrc = "";
    private String pathCmp = "";
    private final ArrayList<String> deletes = new ArrayList<String>();
    private final ArrayList<String> changes = new ArrayList<String>();
    private final ArrayList<String> adds = new ArrayList<String>();

    public TXTchecker compare() throws IOException {

        FileInputStream fs = new FileInputStream(pathSrc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        Vector<String> v1 = new Vector<>();
        Vector<String> v2 = new Vector<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] temp = line.split(" ");
            Collections.addAll(v1, temp);
        }
        br.close();

        fs = new FileInputStream(pathCmp);
        br = new BufferedReader(new InputStreamReader(fs));
        while ((line = br.readLine()) != null) {
            String[] temp = line.split(" ");
            Collections.addAll(v2, temp);
        }
        br.close();

        int length = v2.size() + 1;
        Vector<String> originVec = new Vector<>(v1);
        int[] record = new int[length];
        String[][] strRecords = new String[length][2];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < 2; j++) {
                strRecords[i][j] = " ";
            }
            record[i] = 0;
        }

        v1Index = 0;
        v2Index = 0;
        String[] changeArr = new String[length];
        String[] addArr = new String[length];
        String[] deleteArr = new String[length];
        while (true) {
            if (v1Index >= v1.size() || v2Index >= v2.size()) {
                break;
            }
            if (!v1.get(v1Index).equals(v2.get(v2Index))) {
                if (judgeForDelete(v1, v2, v1Index, v2Index, record, strRecords)) {
                    for(int i = v2Index; i < v1Index; ++i)
                        v1.remove(v2Index);
                    v1Index = v2Index;
                    flag_delete = true;
                } else if (judgeForAdd(v1, v2, v1Index = v2Index, v2Index, record, strRecords)) {
                    flag_add = true;
                    v1Index = v2Index;
                } else {
                    v2Index = v1Index;
                    String index = "index#" + (v2Index + 1) + ": ";
                    changes.add(index + v1.get(v1Index) + "->" + v2.get(v2Index));
                    changeArr[v2Index] = v2.get(v2Index);
                    v1.set(v1Index, v2.get(v2Index));
                }
            }
            flag_add = true;
            flag_delete = true;
            v1Index++;
            v2Index++;
        }

        while(v1.size() < v2.size())
        {
            int sub = v2.size() - v1.size();
            v1.insertElementAt(v2.get(v2.size() - sub), v1.size()-1);
            record[v1.size()-1] += 1;
            strRecords[v1.size()-1][1] = v2.get(v2.size() - sub);
        }

        for(; v1.size() > v2.size();) {
            record[v1.size() - 1] += 1;
            strRecords[v1.size() - 1][0] = v1.get(v1.size() - 1);
            v1.remove(v1.size() - 1);
        }

        for (int i = 0; i < length; i++) {
            if (record[i] % 2 == 0 && !strRecords[i][0].equals(strRecords[i][1])) {
                //System.out.print("\033[34m" + "change: " + strRecords[i][0] + " into " + strRecords[i][1] + "\033[0m"+ " ");
                String index = "index#" + (i + 1) + ": ";
                changes.add(index + strRecords[i][0] + "->" + strRecords[i][1]);
                changeArr[i] = strRecords[i][1];
            }
            if (record[i] % 2 == 1) {
                if (!strRecords[i][0].equals(" ")) {
                    //System.out.print("\033[31m" + "delete: " + strRecords[i][0] + "\033[0m"+" ");
                    String index = "index#" + (i + 1) + ": ";
                    deletes.add(index + strRecords[i][0]);
                    deleteArr[i] = strRecords[i][0];
                } else if (!strRecords[i][1].equals(" ")) {
                    //System.out.print("\033[33m" + "add: " + strRecords[i][1] + "\033[0m"+" ");
                    String index = "index#" + (i + 1) + ": ";
                    adds.add(index + strRecords[i][1]);
                    addArr[i] = strRecords[i][1];
                }
            }
        }


        for(int i = 0; i < originVec.size(); ++i)
        {
            int len = originVec.size();
            if(changeArr[i] != null)
            {
                //System.out.print("\033[34m" + changeArr[i] + "(" + originVec.get(i) + ") " +  "\033[0m");
                text += changeArr[i] + "(" + originVec.get(i) + ") ";
            }else if(addArr[i] != null)
            {
                originVec.add(originVec.get(originVec.size()-1));
                for(int j = len - 1; j > i; --j)
                {
                    originVec.setElementAt(originVec.get(j-1),j);
                }
                //System.out.print("\033[33m" + addArr[i] + " " + "\033[0m");
                text += "(" + addArr[i] + ") ";
            }else if(deleteArr[i] != null)
            {
                originVec.setElementAt("",i);
                //System.out.print( "\033[31m" + "("+ deleteArr[i] + ") " + "\033[0m"+" ");
                text += "(" + deleteArr[i] + ") ";
            }else {
                //System.out.print(originVec.get(i) + " ");
                text += originVec.get(i) + " ";
            }
        }

        for(int i = originVec.size();i< length;++i)
        {
            if (deleteArr[i] != null) {
                //System.out.print( "\033[31m" + "("+ deleteArr[i] + ") " + "\033[0m"+" ");
                text += "(" + deleteArr[i] + ") ";
                deletes.add(deleteArr[i]);
            }
            else if(addArr[i] != null) {
                //System.out.print("\033[33m" + addArr[i] + " " + "\033[0m");
                text += "(" + addArr[i] + ") ";
                adds.add(addArr[i]);
            }
        }
        return this;
    }

    private boolean judgeForDelete(Vector<String> v1, Vector<String> v2, int v1Index, int v2Index,
                                   int[] record, String[][] str) {
        int origin = v1Index;

        if (v1.get(v1Index).equals(v2.get(v2Index))) {
            return true;
        }

        if (v1Index + 1 >= v1.size()) {
            this.v1Index = v1Index + 1;
            flag_delete = false;
            return false;
        }
        if (judgeForDelete(v1, v2, this.v1Index = ++v1Index, v2Index, record, str)) {
            record[origin] += 1;
            str[origin][0] = v1.get(origin);
        }

        return flag_delete;
    }

    private boolean judgeForAdd(Vector<String> v1, Vector<String> v2, int v1Index, int v2Index, int[] record, String[][] str) {
        int origin = v2Index;
        if (v1.get(v1Index).equals(v2.get(v2Index))) {
            return true;
        }

        if (v2Index + 1 >= v2.size()) {
            this.v2Index = v2Index + 1;
            flag_add = false;
            return false;
        }

        if (judgeForAdd(v1, v2, v1Index, this.v2Index = ++v2Index, record, str)) {
            v1.insertElementAt(v2.get(origin), v1Index);
            record[origin] += 1;
            str[origin][1] = v2.get(origin);
        }

        return flag_add;
    }

    public ArrayList<String> getAdds() {
        return adds;
    }

    public ArrayList<String> getDeletes() {
        return deletes;
    }

    public ArrayList<String> getChanges() {
        return changes;
    }

    public String getText() {
        return text;
    }
}


