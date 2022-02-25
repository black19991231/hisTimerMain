package com.example.histimermain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Memo extends AppCompatActivity {
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button saveButton = (Button) findViewById(R.id.save_button);
        Button clearButton = (Button) findViewById(R.id.clear_button);
        ListView listView = (ListView) findViewById(R.id.memo_list);

        // ListViewに対してアイテムを動的に追加していく場合はadapterを利用します
        // simple_list_item_1 はデフォルトで提供されているTextViewのみ持つレイアウトです。
        // 自作したLayoutを指定して複雑なリストも作成可能です。
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        // 保存ボタンをタップ時の処理
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // エディタの内容を取得します
                String memo = editText.getText().toString();

                // メモ内容が入力されていない場合は保存しないようにします
                if (memo.isEmpty()) {
                    // Toastを使ってメッセージを表示します
                    Toast.makeText(getApplicationContext(), "メモ内容が入力されていません", Toast.LENGTH_SHORT).show();
                    return;
                }

                // リストにメモ内容を反映します
                adapter.add(memo);

                // エディタはクリアします
                editText.setText("");
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // エディタに空文字をセットします
                editText.setText("");
            }
        });
    }

    private String fileName = "memo.txt";
    private String splitStr = "///";

    // 保存されているメモを取得する
    private String[] readMemo() {
        BufferedReader reader = null;
        String memoTxt = "";
        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String str;
            while((str = reader.readLine()) != null) {
                memoTxt = memoTxt + str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // メモが保存されていない場合はnullを返す
        if (memoTxt == "") {
            return null;
        }

        // 区切り文字で区切って返す
        return memoTxt.split(splitStr);
    }

    // メモを端末に保存する
    private void saveMemo(String str) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE)));
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}