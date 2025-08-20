package chatbot.java;

import java.util.ArrayList;
import java.util.List;

public class Responder 
{
    private final List<String> respostas;


    public Responder() 
    {
        respostas = new ArrayList<>();
        inicializarRespostas();//Adiciona todas as possibilidades de respostas dentro das Arrays
    }

    public String generateResponse(int type) 
    {
        if (type < 0 || type >= respostas.size())//Verifica se esta dentro dos limites do ArrayList
        {
            throw new IndexOutOfBoundsException("Tipo de resposta inválido");
        }
        return respostas.get(type);
    }

    private void inicializarRespostas() 
    {
        respostas.add(0, 
    "Olá, eu sou o FrogBot🐸.\n"+
    "Estou aqui para apresentar o meu criador! Oque gostaria de saber?"
    );

    respostas.add(1, 
        "O meu criador, Mateus Lima, está graduando Sistemas de Informação na UTFPR,atualmente no terceiro período. Apesar de não estar há muito tempo na faculdade, ele buscou diversos conhecimentos por fora, realizando cursos, onde aprendeu HTML, CSS, JavaScript e SQL, e também participando de projetos, como GameJams e no meu próprio desenvolvimento! Com isso Mateus desenvolveu um amplo conhecimento com linguagens como Java, C#, C++, C e Python. Agora seu objetivo é conseguir um estágio para continuar sua evolução como programador!🤖 "
    );

    respostas.add(2, 
        "Por enquanto como projeto público só existe eu, porém fiquei sabendo que novos projetos já estão sendo desenvolvidos! Então fica de olho no portfólio/github dele 👀 https://github.com/MateusLW"
    );

    respostas.add(3, 
        "Se você se interessou e quer entrar em contato, aqui algumas formas!\n\n"+
        "LinkedIn: https://www.linkedin.com/in/mateus-lima-weigert/\n"+
        "Gmail: mateuslimaw@gmail.com"
    );
        
    }
}


