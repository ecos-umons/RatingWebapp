package uni.umons.ratingwebapp.domain.dto;

public class RatedGitUserDto {
    private Long id;
    private String name;
    private String repository;
    private int rate1;
    private int rateDifficulty1;
    private String rateDescription1;
    private String rater1;
    private int rate2;
    private int rateDifficulty2;
    private String rateDescription2;
    private String rater2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public int getRate1() {
        return rate1;
    }

    public void setRate1(int rate1) {
        this.rate1 = rate1;
    }

    public int getRateDifficulty1() {
        return rateDifficulty1;
    }

    public void setRateDifficulty1(int rateDifficulty1) {
        this.rateDifficulty1 = rateDifficulty1;
    }

    public String getRateDescription1() {
        return rateDescription1;
    }

    public void setRateDescription1(String rateDescription1) {
        this.rateDescription1 = rateDescription1;
    }

    public String getRater1() {
        return rater1;
    }

    public void setRater1(String rater1) {
        this.rater1 = rater1;
    }

    public int getRate2() {
        return rate2;
    }

    public void setRate2(int rate2) {
        this.rate2 = rate2;
    }

    public int getRateDifficulty2() {
        return rateDifficulty2;
    }

    public void setRateDifficulty2(int rateDifficulty2) {
        this.rateDifficulty2 = rateDifficulty2;
    }

    public String getRateDescription2() {
        return rateDescription2;
    }

    public void setRateDescription2(String rateDescription2) {
        this.rateDescription2 = rateDescription2;
    }

    public String getRater2() {
        return rater2;
    }

    public void setRater2(String rater2) {
        this.rater2 = rater2;
    }
}
