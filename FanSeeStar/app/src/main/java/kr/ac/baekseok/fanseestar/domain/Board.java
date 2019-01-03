package kr.ac.baekseok.fanseestar.domain;

public class Board {
    private int boardId;
    private String artistName;
    private String writer;
    private String boardTitle;
    private String boardContent;
    private String boardImage;

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public String getBoardImage() {
        return boardImage;
    }

    public void setBoardImage(String boardImage) {
        this.boardImage = boardImage;
    }
}
