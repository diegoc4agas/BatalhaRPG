abstract class Personagem {
    String nome;
    int vida;
    int ataque;

    public Personagem(String nome, int vida, int ataque) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
    }

    public abstract void atacar(Personagem alvo, int tipoDado);

    public void receberDano(int dano) {
        vida -= dano;
        System.out.println(" vida restante: " + Math.max(vida, 0));
    }

    public boolean estaVivo() {
        return vida > 0;
    }
}

class Guerreiro extends Personagem {
    public Guerreiro(String nome) {
        super(nome, 100, 10);
    }

    @Override
    public void atacar(Personagem alvo, int tipoDado) {
        if (BatalhaRPG.chanceDeErro(tipoDado)) {
            System.out.println(nome + " errou o ataque! 😱");
        } else {
            int dano = ataque + BatalhaRPG.rolarDado(tipoDado);
            System.out.printf(nome+ " atingiu " +alvo.nome+ " com " + dano + " de dano,");
            alvo.receberDano(dano);
        }
    }
}

class Mago extends Personagem {
    public Mago(String nome) {
        super(nome, 61, 12);
    }

    @Override
    public void atacar(Personagem alvo, int tipoDado) {
        if (BatalhaRPG.chanceDeErro(tipoDado)) {
            System.out.println(nome+ " errou o feitiço! 🌀");
        } else {
            int dano = ataque + BatalhaRPG.rolarDado(tipoDado);
            System.out.printf(nome+ " lançou um feitiço causando " +dano+ " de dano,");
            alvo.receberDano(dano);
        }
    }
}

class Arqueiro extends Personagem {
    public Arqueiro(String nome) {
        super(nome, 90, 9);
    }

    @Override
    public void atacar(Personagem alvo, int tipoDado) {
        if (BatalhaRPG.chanceDeErro(tipoDado)) {
            System.out.println(nome+ " disparou uma flecha, mas errou! ❌");
        } else {
            int dano = ataque + BatalhaRPG.rolarDado(tipoDado);
            System.out.printf(nome+ " disparou uma flecha causando " +dano+ " de dano,");
            alvo.receberDano(dano);
        }
    }
}

class Inimigo extends Personagem {
    public Inimigo(String nome, int vida, int ataque) {
        super(nome, vida, ataque);
    }

    @Override
    public void atacar(Personagem alvo, int tipoDado) {
        int dano = ataque + BatalhaRPG.rolarDado(6); // O inimigo sempre usa um D6
        System.out.printf(nome+ " atacou " + alvo.nome + " e causou " + dano + " de dano,");
        alvo.receberDano(dano);
    }
}
