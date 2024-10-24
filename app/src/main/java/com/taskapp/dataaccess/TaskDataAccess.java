package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taskapp.model.Task;
import com.taskapp.model.User;

public class TaskDataAccess {

    private final String filePath;

    private final UserDataAccess userDataAccess;

    public TaskDataAccess() {
        filePath = "app/src/main/resources/tasks.csv";
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param filePath
     * @param userDataAccess
     */
    public TaskDataAccess(String filePath, UserDataAccess userDataAccess) {
        this.filePath = filePath;
        this.userDataAccess = userDataAccess;
    }

    /**
     * CSVから全てのタスクデータを取得します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @return タスクのリスト
     */
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>(); // 空のTaskオブジェクトを格納するリストを初期化
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // ヘッダー行をスキップ
            
            String line; //ファイルの各行を読み込む
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); //読み込んだ行をカンマで分割し、配列dataに格納
                if (data.length == 4) {
                    try {
                        int code = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        int status = Integer.parseInt(data[2].trim());
                        int repUserCode = Integer.parseInt(data[3].trim());
    
                        // ユーザー情報を取得
                        User repUser = userDataAccess.findByCode(repUserCode); // repUserCodeを使って、対応するユーザー情報を取得
                        tasks.add(new Task(code, name, status, repUser));
                    } catch (NumberFormatException e) {
                        System.out.println("数値変換エラー: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    

    
    

    


    /**
     * タスクをCSVに保存します。
     * @param task 保存するタスク
     */
    public void save(Task task) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
        bw.write(createLine(task));
        bw.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private String createLine(Task task) {
    return String.format("%d,%s,%d,%d",
            task.getCode(),
            task.getName(),
            task.getStatus(),
            task.getRepUser().getCode());
}


    /**
     * コードを基にタスクデータを1件取得します。
     * @param code 取得するタスクのコード
     * @return 取得したタスク
     */
    // public Task findByCode(int code) {
    //     try () {

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    /**
     * タスクデータを更新します。
     * @param updateTask 更新するタスク
     */
    // public void update(Task updateTask) {
    //     try () {

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * コードを基にタスクデータを削除します。
     * @param code 削除するタスクのコード
     */
    // public void delete(int code) {
    //     try () {

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * タスクデータをCSVに書き込むためのフォーマットを作成します。
     * @param task フォーマットを作成するタスク
     * @return CSVに書き込むためのフォーマット文字列
     */
    // private String createLine(Task task) {
    // }
}