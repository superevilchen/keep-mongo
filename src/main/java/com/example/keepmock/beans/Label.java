package com.example.keepmock.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Label implements Comparable<Label> {

    private String labelName;
    private Color color;

    @Override
    public int compareTo(Label o) {
        return this.labelName.compareTo(o.getLabelName());
    }
}
