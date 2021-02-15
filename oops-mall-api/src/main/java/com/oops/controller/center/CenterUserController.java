package com.oops.controller.center;

import com.oops.config.FileUpload;
import com.oops.controller.BaseController;
import com.oops.pojo.Users;
import com.oops.pojo.bo.CenterUserBO;
import com.oops.service.center.CenterUserService;
import com.oops.utils.CookieUtils;
import com.oops.utils.JsonUtils;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Map;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月30日 16:16
 */
@Api(value = "用户信息更新相关controller", tags = {"用户信息更新相关接口"})
@RestController
@RequestMapping("/userInfo")
public class CenterUserController extends BaseController {
    @Autowired
    CenterUserService centerUserService;
    @Autowired
    FileUpload fileUpload;

    @PostMapping("/update")
    public OopsResult updateUserInfo(@RequestParam String userId, @RequestBody @Valid CenterUserBO centerUserBO,
                                     HttpServletRequest request, HttpServletResponse response,
                                     BindingResult result) {

        //判断BindingResult是否保存错误的验证信息
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return OopsResult.errorMap(errorMap);
        }

        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        Users resultUser = setNullProperty(users);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(resultUser), true);

        //TODO 增加令牌token 整合进redis分布式会话
        return OopsResult.ok(resultUser);
    }


    @PostMapping("/uploadFace")
    public OopsResult uploadFace(@RequestParam String userId, MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
        //上传头像保存的服务端位置    E:home/face
        String fileSpace = fileUpload.getUserFaceLocation();
        //                       /userId
        String idPreFix = File.separator + userId;
        //保存至数据库的图片地址
        String sqlPath = null;

        //文件上传
        if (file != null) {
            //获得本地图片的路径
            String filename = file.getOriginalFilename();
            //对本地文件名修改   统一样式为 face-userId.png
            if (StringUtils.isNotBlank(filename)) {
                String[] fileNameArr = filename.split("\\.");
                String suffix = fileNameArr[fileNameArr.length - 1];
                if(!suffix.equals("png") && !suffix.equals("jpg") && !suffix.equals("jpeg")){
                    return OopsResult.errorMsg("不支持的图片格式，请用.png或.jpg或.jpeg的格式");
                }
                String newFileName = "face-" + userId + "." + suffix;

                //最终拼接完成的上传头像保存位置                          E:home/face/userId/face-userId.png
                String finalFacePath = fileSpace + idPreFix + File.separator + newFileName;
                //用于保存至数据库的地址，针对浏览地的缓存,加上时间戳         http://localhost:8088/userId/face-userId.png?t=...
                sqlPath = fileUpload.getImageServerUrl()+idPreFix+"/"+newFileName+"?t="+System.currentTimeMillis();

                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null) {
                    outFile.getParentFile().mkdirs();
                }

                //文件写入保存至服务端目录
                try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                     InputStream inputStream = file.getInputStream()) {
                    IOUtils.copy(inputStream, fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return OopsResult.errorMsg("文件不能为空");
        }

        //更新用户头像到数据库
        Users updateUser = centerUserService.updateFace(userId, sqlPath);
        Users resultUser = setNullProperty(updateUser);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(resultUser), true);

        //TODO 增加令牌token 整合进redis分布式会话
        return OopsResult.ok(updateUser);
    }
}

