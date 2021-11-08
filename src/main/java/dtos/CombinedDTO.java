package dtos;

public class CombinedDTO
{
    private String joke1;
    private String joke1Reference;
    private String joke2;
    private String joke2Reference;

    public CombinedDTO(ChuckDTO chuckDTO, DadDTO dadDTO)
    {
        joke1 = chuckDTO.getValue();
        joke1Reference = chuckDTO.getUrl();
        joke2 = dadDTO.getJoke();
        joke2Reference = "https://icanhazdadjoke.com/j/" + dadDTO.getId();
    }

    public String getJoke1()
    {
        return joke1;
    }

    public void setJoke1(String joke1)
    {
        this.joke1 = joke1;
    }

    public String getJoke1Reference()
    {
        return joke1Reference;
    }

    public void setJoke1Reference(String joke1Reference)
    {
        this.joke1Reference = joke1Reference;
    }

    public String getJoke2()
    {
        return joke2;
    }

    public void setJoke2(String joke2)
    {
        this.joke2 = joke2;
    }

    public String getJoke2Reference()
    {
        return joke2Reference;
    }

    public void setJoke2Reference(String joke2Reference)
    {
        this.joke2Reference = joke2Reference;
    }
}
