#include "TXTchecker.h"
#include <fstream>
#include <vector>
#include <string>

bool flag_add = true;
bool flag_delete = true;

using std::string, std::vector, std::cout, std::endl;

bool judgeForDelete(vector<string> &v1, vector<string> &v2, int &v1Index, int &v2Index, int *record, string **str);

bool judgeForAdd(vector<string> &v1, vector<string> &v2, int &v1Index, int &v2Index, int *record, string **str);

TXTchecker::TXTchecker(const string &m_pathSrc, const string &m_pathCmp) {
    pathSrc = m_pathSrc;
    pathCmp = m_pathCmp;
}

void TXTchecker::compare() {
    std::fstream fs;
    fs.open(pathSrc);
    vector<string> v1;
    vector<string> v2;

    string str;
    while (fs >> str) {
        v1.push_back(str);
    }

    fs.close();
    fs.open(pathCmp);

    while (fs >> str) {
        v2.push_back(str);
    }

    unsigned long length = v2.size() + 1;
    int *record = new int[length];
    auto **strRecords = new string *[length];
    for (int i = 0; i < length; i++) {
        strRecords[i] = new string[2];
        for (int j = 0; j < 2; j++) {
            strRecords[i][j] = " ";
        }
        *(record + i) = 0;
    }
    fs.close();

    int v1Index = 0;
    int v2Index = 0;
    while (true) {
        if (v1Index >= v1.size() && v2Index >= v2.size())
            break;
        if (v1[v1Index] != v2[v2Index]) {
            if (judgeForDelete(v1, v2, v1Index, v2Index, record, strRecords)) {
                v1.erase(v1.begin() + v2Index, v1.begin() + v1Index);
                v1Index = ++v2Index;
                flag_delete = true;
                while (v1.size() < v2.size()) {
                    v1.insert(v1.begin() + v1Index, v2[v2Index]);
                    *(record + v1Index) += 1;
                    strRecords[v1Index][1] = v2[v2Index];
                    v1Index++;
                    v2Index++;
                }
            } else if (judgeForAdd(v1, v2, v1Index = v2Index, v2Index, record, strRecords)) {
                flag_add = true;
                v1Index = v2Index;
            } else {
                v2Index = v1Index;
                if (v1Index == v1.size())
                    cout << "\033[34m" << "add: " << v2[v2Index] << "\033[0m" << endl;
                else
                    cout << "\033[34m" << "change: " << v1[v1Index] << " into " << v2[v2Index] << "\033[0m" << endl;
                string index = "index#" + std::to_string(v2Index + 1) + ": ";
                changes.push_back(index + v1[v1Index] + "->" + v2[v2Index]);
                v1[v1Index] = v2[v2Index];
            }
        }
        flag_add = true;
        flag_delete = true;
        v1Index++;
        v2Index++;
    }

    for (int i = 0; i < length; i++) {
        if (record[i] == 2 && strRecords[i][0] != strRecords[i][1]) {
            cout << "\033[34m" << "change: " << strRecords[i][0] << " into " << strRecords[i][1] << "\033[0m" << endl;
            string index = "index#" + std::to_string(i + 1) + ": ";
            changes.push_back(index + v1[v1Index] + "->" + v2[v2Index]);
        }
        if (record[i] == 1) {
            if (strRecords[i][0] != " ") {
                cout << "\033[31m" << "delete: " << strRecords[i][0] << "\033[0m" << endl;
                string index = "index#" + std::to_string(i + 1) + ": ";
                deletes.push_back(index + strRecords[i][0]);
            } else if (strRecords[i][1] != " ") {
                cout << "\033[33m" << "add: " << strRecords[i][1] << "\033[0m" << endl;
                string index = "index#" + std::to_string(i + 1) + ": ";
                adds.push_back(index + strRecords[i][1]);
            }
        }
    }

    delete[] record;
    for (int i = 0; i < length; i++) {
        delete[] *(strRecords + i);
    }
    delete[] strRecords;
}

vector<string> &TXTchecker::getAdd() {
    return adds;
}

vector<string> &TXTchecker::getDelete() {
    return deletes;
}

vector<string> &TXTchecker::getChange() {
    return changes;
}

void TXTchecker::save(const string &pathDest) {

}


bool judgeForDelete(vector<string> &v1, vector<string> &v2, int &v1Index, int &v2Index, int *record, string **str) {
    int origin = v1Index;

    if (v1[v1Index] == v2[v2Index]) {
        return true;
    }

    if (v1Index + 1 >= v1.size()) {
        v1Index++;
        flag_delete = false;
        return false;
    }
    if (judgeForDelete(v1, v2, ++v1Index, v2Index, record, str)) {
        *(record + origin) += 1;
        str[origin][0] = v1[origin];
    }
    return flag_delete;
}

bool judgeForAdd(vector<string> &v1, vector<string> &v2, int &v1Index, int &v2Index, int *record, string **str) {
    int origin = v2Index;
    if (v1[v1Index] == v2[v2Index]) {
        return true;
    }

    if (v2Index + 1 >= v2.size()) {
        v2Index++;
        flag_add = false;
        return false;
    }

    if (judgeForAdd(v1, v2, v1Index, ++v2Index, record, str)) {
        v1.insert(v1.begin() + origin, v2[origin]);
        *(record + origin) += 1;
        str[origin][1] = v2[origin];
    }
    return flag_add;
}