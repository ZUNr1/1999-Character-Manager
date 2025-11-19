package com.ZUNr1.model;

public class Portrait {
    private String portraitDescribe;
    private String firstPortrait;
    private String secondPortrait;
    private String thirdPortrait;
    private String fourthPortrait;
    private String fifthPortrait;

    public Portrait(){}
    private Portrait(PortraitBuilder portraitBuilder) {
        this.portraitDescribe = portraitBuilder.portraitDescribe;
        this.firstPortrait = portraitBuilder.firstPortrait;
        this.secondPortrait = portraitBuilder.secondPortrait;
        this.thirdPortrait = portraitBuilder.thirdPortrait;
        this.fourthPortrait = portraitBuilder.fourthPortrait;
        this.fifthPortrait = portraitBuilder.fifthPortrait;
    }

    public String getPortraitDescribe() {
        return portraitDescribe;
    }

    public String getFirstPortrait() {
        return firstPortrait;
    }

    public String getSecondPortrait() {
        return secondPortrait;
    }

    public String getThirdPortrait() {
        return thirdPortrait;
    }

    public String getFourthPortrait() {
        return fourthPortrait;
    }

    public String getFifthPortrait() {
        return fifthPortrait;
    }

    public static class PortraitBuilder {
        private String portraitDescribe;
        private String firstPortrait;
        private String secondPortrait;
        private String thirdPortrait;
        private String fourthPortrait;
        private String fifthPortrait;

        public PortraitBuilder(String portraitDescribe) {
            validateMust(portraitDescribe);
            this.portraitDescribe = portraitDescribe;
            this.firstPortrait = "";
            this.secondPortrait = "";
            this.thirdPortrait = "";
            this.fourthPortrait = "";
            this.fifthPortrait = "";
        }

        public PortraitBuilder firstPortrait(String firstPortrait) {
            if (!validate(firstPortrait)) {
                this.firstPortrait = "";
            } else {
                this.firstPortrait = firstPortrait;
            }
            return this;
        }

        public PortraitBuilder secondPortrait(String secondPortrait) {
            if (!validate(secondPortrait)) {
                this.secondPortrait = "";
            } else {
                this.secondPortrait = secondPortrait;
            }
            return this;
        }

        public PortraitBuilder thirdPortrait(String thirdPortrait) {
            if (!validate(thirdPortrait)) {
                this.thirdPortrait = "";
            } else {
                this.thirdPortrait = thirdPortrait;
            }
            return this;
        }

        public PortraitBuilder fourthPortrait(String fourthPortrait) {
            if (!validate(fourthPortrait)) {
                this.fourthPortrait = "";
            } else {
                this.fourthPortrait = fourthPortrait;
            }
            return this;
        }

        public PortraitBuilder fifthPortrait(String fifthPortrait) {
            if (!validate(fifthPortrait)) {
                this.fifthPortrait = "";
            } else {
                this.fifthPortrait = fifthPortrait;
            }
            return this;
        }

        public Portrait build() {
            return new Portrait(this);
        }

        private boolean validate(String s) {
            if (s == null || s.trim().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        private void validateMust(String s) {
            if (s == null || s.trim().isEmpty()) {
                throw new IllegalArgumentException("塑造名字有空值");
            }
        }
    }
}
