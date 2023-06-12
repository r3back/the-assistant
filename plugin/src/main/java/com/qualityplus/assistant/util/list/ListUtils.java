package com.qualityplus.assistant.util.list;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ListUtils {
    public List<Integer> secuence(final int begin, final int end) {
        final List<Integer> ret = new ArrayList<>(end - begin + 1);
        for (int i = begin; i<=end; i++) {
            ret.add(i);
        }
        return ret;
    }

    public List<String> stringSecuence(final int begin, final int end) {
        final List<String> ret = new ArrayList<>(end - begin + 1);
        for (int i = begin; i <= end; i++) {
            ret.add(String.valueOf(i));
        }
        return ret;
    }

    public <T> List<T> listWith(final List<T> list, final T toAdd) {
        list.add(toAdd);
        return list;
    }

    public <T> List<T> listWithout(final List<T> list, final T toAdd) {
        list.add(toAdd);
        return list;
    }

    public static class ListBuilder<T>{
        private final List<T> initialList;

        private ListBuilder(final List<T> initialList) {
            this.initialList = new ArrayList<>(initialList);
        }

        public static <T> ListBuilder<T> of(final List<T> list){
            return new ListBuilder<>(list);
        }

        public ListBuilder<T> with(final List<T> with){
            this.initialList.addAll(with);
            return this;
        }

        public List<T> get(){
            return this.initialList;
        }
    }
}
