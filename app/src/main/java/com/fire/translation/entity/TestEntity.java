package com.fire.translation.entity;

import java.util.List;

/**
 * Created by fire on 2017/12/28.
 * Date：2017/12/28
 * Author: fire
 * Description:
 */

public class TestEntity {

    /**
     * word_name : 时间
     * symbols : [{"word_symbol":"shí jiān","symbol_mp3":"http://res.iciba
     * .com/hanyu/ci/2f/c0/2fc0d4c8ca346b5f351c08f56ec73366.mp3","parts":[{"part_name":"",
     * "means":[{"word_mean":"time","has_mean":"1","split":1},{"word_mean":"hour","has_mean":"1",
     * "split":1},{"word_mean":"while","has_mean":"1","split":1},{"word_mean":"[电影]Waati",
     * "has_mean":"0","split":0}]}],"ph_am_mp3":"","ph_en_mp3":"","ph_tts_mp3":"","ph_other":""}]
     */

    private String word_name;
    private List<SymbolsBean> symbols;

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public List<SymbolsBean> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<SymbolsBean> symbols) {
        this.symbols = symbols;
    }

    public static class SymbolsBean {
        /**
         * word_symbol : shí jiān
         * symbol_mp3 : http://res.iciba.com/hanyu/ci/2f/c0/2fc0d4c8ca346b5f351c08f56ec73366.mp3
         * parts : [{"part_name":"","means":[{"word_mean":"time","has_mean":"1","split":1},
         * {"word_mean":"hour","has_mean":"1","split":1},{"word_mean":"while","has_mean":"1",
         * "split":1},{"word_mean":"[电影]Waati","has_mean":"0","split":0}]}]
         * ph_am_mp3 :
         * ph_en_mp3 :
         * ph_tts_mp3 :
         * ph_other :
         */

        private String word_symbol;
        private String symbol_mp3;
        private String ph_am_mp3;
        private String ph_en_mp3;
        private String ph_tts_mp3;
        private String ph_other;
        private List<PartsBean> parts;

        public String getWord_symbol() {
            return word_symbol;
        }

        public void setWord_symbol(String word_symbol) {
            this.word_symbol = word_symbol;
        }

        public String getSymbol_mp3() {
            return symbol_mp3;
        }

        public void setSymbol_mp3(String symbol_mp3) {
            this.symbol_mp3 = symbol_mp3;
        }

        public String getPh_am_mp3() {
            return ph_am_mp3;
        }

        public void setPh_am_mp3(String ph_am_mp3) {
            this.ph_am_mp3 = ph_am_mp3;
        }

        public String getPh_en_mp3() {
            return ph_en_mp3;
        }

        public void setPh_en_mp3(String ph_en_mp3) {
            this.ph_en_mp3 = ph_en_mp3;
        }

        public String getPh_tts_mp3() {
            return ph_tts_mp3;
        }

        public void setPh_tts_mp3(String ph_tts_mp3) {
            this.ph_tts_mp3 = ph_tts_mp3;
        }

        public String getPh_other() {
            return ph_other;
        }

        public void setPh_other(String ph_other) {
            this.ph_other = ph_other;
        }

        public List<PartsBean> getParts() {
            return parts;
        }

        public void setParts(List<PartsBean> parts) {
            this.parts = parts;
        }

        public static class PartsBean {
            /**
             * part_name :
             * means : [{"word_mean":"time","has_mean":"1","split":1},{"word_mean":"hour",
             * "has_mean":"1","split":1},{"word_mean":"while","has_mean":"1","split":1},
             * {"word_mean":"[电影]Waati","has_mean":"0","split":0}]
             */

            private String part_name;
            private List<MeansBean> means;

            public String getPart_name() {
                return part_name;
            }

            public void setPart_name(String part_name) {
                this.part_name = part_name;
            }

            public List<MeansBean> getMeans() {
                return means;
            }

            public void setMeans(List<MeansBean> means) {
                this.means = means;
            }

            public static class MeansBean {
                /**
                 * word_mean : time
                 * has_mean : 1
                 * split : 1
                 */

                private String word_mean;
                private String has_mean;
                private int split;

                public String getWord_mean() {
                    return word_mean;
                }

                public void setWord_mean(String word_mean) {
                    this.word_mean = word_mean;
                }

                public String getHas_mean() {
                    return has_mean;
                }

                public void setHas_mean(String has_mean) {
                    this.has_mean = has_mean;
                }

                public int getSplit() {
                    return split;
                }

                public void setSplit(int split) {
                    this.split = split;
                }
            }
        }
    }
}
