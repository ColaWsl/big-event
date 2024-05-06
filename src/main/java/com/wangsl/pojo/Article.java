package com.wangsl.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangsl.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

    @NotNull(groups = {Update.class})
    private Integer id;//主键ID

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;//文章标题

    @NotEmpty
    private String content;//文章内容

    @NotEmpty
    @URL
    private String coverImg;//封面图像

    @State
    private String state;//发布状态 已发布|草稿

    @NotNull
    private Integer categoryId;//文章分类id

    private Integer createUser;//创建人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    public interface Update extends Default {};
    public interface Add extends Default {};
}
