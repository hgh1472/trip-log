package toy.triplog.domain.nation;

public enum Continent {
    ASIA("아시아"),
    EUROPE("유럽"),
    NORTH_AMERICA("북아메리카"),
    SOUTH_AMERICA("남아메리카"),
    OCEANIA("오세아니아"),
    AFRICA("아프리카"),
    ANTARCTICA("남극"),
    UNKNOWN("알 수 없음");

    private final String name;

    Continent(String name) {
        this.name = name;
    }

}
