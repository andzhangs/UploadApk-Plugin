package cn.andzhang.android;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import java.io.File;
import cn.andzhang.android.exts.ConfigExtension;
import cn.andzhang.android.http.HttpRequest;
import cn.andzhang.android.util.Logger;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/24 14:01
 * @description 检测(或生成)新安装包后，上传到蒲公英或Fir.im，并通知到钉钉群
 */
public class UploadApkPlugin implements Plugin<Project> {

    /**
     * 自定义GroupId
     */
    private static final String GROUP_ID_UPLOAD = "plugin upload apk";
    /**
     * 自定义TaskName
     */
    private static final String TASK_NAME_UPLOAD = "uploadApkFile";
    /**
     * 自定义ClosureName
     */
    private static final String CLOSURE_NAME = "uploadApk";
    /**
     * 配置扩展属性
     */
    private ConfigExtension mConfigExtension;

    @Override
    public void apply(Project project) {

        mConfigExtension = project.getExtensions().create(CLOSURE_NAME, ConfigExtension.class);

        //运行build apk后，调起自定义task
        project.afterEvaluate(pro -> pro.getTasks().matching(task -> mConfigExtension.taskName.equals(task.getName())).forEach(task -> {
            File file = new File(mConfigExtension.jsonPath);
            if (file.exists()) {
                task.finalizedBy(TASK_NAME_UPLOAD);
                HttpRequest httpRequest = new HttpRequest(file);
                createUploadTask(project, httpRequest);
            } else {
                Logger.print("配置文件不存在");
            }
        }));
    }

    private void createUploadTask(Project project, HttpRequest httpRequest) {
        Task taskGetToken = project.task(TASK_NAME_UPLOAD);
        taskGetToken.setGroup(GROUP_ID_UPLOAD);
        taskGetToken.dependsOn(mConfigExtension.taskName);
        taskGetToken.doFirst(task -> httpRequest.getToken());
        taskGetToken.doLast(task -> httpRequest.uploadApk());
    }
}