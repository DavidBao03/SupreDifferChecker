//
// Created by 鲍子硕 on 2023/4/12.
//

#ifndef SUPREDIFFERCHECKER_TXTCHECKER_H
#define SUPREDIFFERCHECKER_TXTCHECKER_H

#include <iostream>
#include <vector>
using std::string,std::vector;
class TXTchecker
{
private:
    std::string pathSrc;
    std::string pathCmp;
    vector<string> deletes;
    vector<string> changes;
    vector<string> adds;
public:
    TXTchecker(const string& m_pathSrc, const string& m_pathCmp);

    void compare();
    vector<string>& getAdd();
    vector<string>& getDelete();
    vector<string>& getChange();
    void save(const string& pathDest);
};

#endif //SUPREDIFFERCHECKER_TXTCHECKER_H
