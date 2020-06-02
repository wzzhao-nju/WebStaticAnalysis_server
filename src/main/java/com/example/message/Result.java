package com.example.message;

import java.util.Vector;

//返回给前端的最终检测结果
public class Result {
    private String filename;
    private int error_count;
    private final Vector<Error> errors;

    public Result() {
        error_count = 0;
        errors = new Vector<Error>();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setError_count(int error_count) {
        this.error_count = error_count;
    }

    public void append(Error error) {
        this.errors.add(error);
        error_count++;
    }

    public String getFilename() {
        return filename;
    }

    public int getError_count() {
        return error_count;
    }

    public Vector<Error> getErrors() {
        return errors;
    }
}

/* 返回检测结果的格式 example
最终的结果按照这样的格式返回给前端
信息包含文件名，缺陷数量，缺陷详细信息（出现行数，上下文，缺陷信息等）
{
    "filename":"main.cpp",
    "error_count":3,
    "errors":[
        {
            "start_line":4,
            "end_line":6,
            "lines_before_error":[
                {
                    "lineNo":1,
                    "code":"int main(){"
                },
                {
                    "lineNo":2,
                    "code":"    cout<<\"hello world\"<<endl;"
                },
                {
                    "lineNo":3,
                    "code":"    cout<<\"xxx\"<<endl;"
                }
            ],
            "error_lines":[
                {
                    "lineNo":4,
                    "code":"    for(int i=0;i<100;i++){"
                },
                {
                    "lineNo":5,
                    "code":"        i--;"
                },
                {
                    "lineNo":6,
                    "code":"    }"
                }
            ],
            "lines_after_error":[
                {
                    "lineNo":7,
                    "code":"    cout<<\"loop finished\"<<endl;"
                },
                {
                    "lineNo":8,
                    "code":"    return 0;"
                },
                {
                    "lineNo":9,
                    "code":"}"
                }
            ],
            "error_info":"检测到死循环缺陷，该循环可能无法在有限时间内退出"
        },
        {
            "start_line":9,
            "end_line":12,
            "lines_before_error":[
                {
                    "lineNo":1,
                    "code":"int main(){"
                },
                {
                    "lineNo":2,
                    "code":"    cout<<\"hello world\"<<endl;"
                },
                {
                    "lineNo":3,
                    "code":"    cout<<\"xxx\"<<endl;"
                }
            ],
            "error_lines":[
                {
                    "lineNo":4,
                    "code":"    for(int i=0;i<100;i++){"
                },
                {
                    "lineNo":5,
                    "code":"        i--;"
                },
                {
                    "lineNo":6,
                    "code":"    }"
                }
            ],
            "lines_after_error":[
                {
                    "lineNo":7,
                    "code":"    cout<<\"loop finished\"<<endl;"
                },
                {
                    "lineNo":8,
                    "code":"    return 0;"
                },
                {
                    "lineNo":9,
                    "code":"}"
                }
            ],
            "error_info":"检测到死循环缺陷，该循环可能无法在有限时间内退出"
        },
    ]
}
 */