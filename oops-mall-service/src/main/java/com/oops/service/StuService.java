package com.oops.service;

import com.oops.pojo.Stu;

/**
 * @author L-N
 */
public interface StuService {
    public Stu getStuInfo(int id);

    public void saveStu();

    public void updateStu(int id);

    public void deleteStu(int id);
}
