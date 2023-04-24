import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        TXTchecker txTchecker = new TXTchecker("C:\\Users\\DvaidBao\\eclipse-workspace\\JAVA practice\\DifferChecker\\srcTxt","C:\\Users\\DvaidBao\\eclipse-workspace\\JAVA practice\\DifferChecker\\compareTxt");
        txTchecker.compare();
        String str = "C:\\Users\\DvaidBao\\eclipse-workspace\\JAVA practice\\DifferChecker\\" + "srcTxt";
        System.out.println(str);
        //C:\Users\DvaidBao\eclipse-workspace\JAVA practice\DifferChecker\srcTxt
        //C:\Users\DvaidBao\eclipse-workspace\JAVA practice\DifferChecker\compareTxt
        System.out.println(txTchecker.getAdds());
        System.out.println(txTchecker.getDeletes());
        System.out.println(txTchecker.getChanges());

        System.out.println(txTchecker.getText());
    }
}
