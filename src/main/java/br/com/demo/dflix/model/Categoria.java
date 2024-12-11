package br.com.demo.dflix.model;

public enum Categoria {
    ACAO("Action"),
    ANIMACAO("Animation"),
    COMEDIA("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    ROMANCE("Romance");
    
    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a String");
    }
}
