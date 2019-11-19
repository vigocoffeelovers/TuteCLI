//esto va en Table 
private Player checkWonTrick() {
    	Player jugadorGanador = null;
    	
    	for (Map.Entry<Player, Cards> entry : currentPlay.entrySet()) {
    		if(jugadorGanador == null) {
    			jugadorGanador=entry.getKey();
    			continue;
    		}
    		if(currentPlay.get(jugadorGanador).getSuit().equals(entry.getValue().getSuit())) {
    			if(currentPlay.get(jugadorGanador).getNumber().compare(entry.getValue().getNumber()) == -1 ) {//El compare no se usa igual que el compareTo, asi que igual hay que cambiarlo
    				jugadorGanador=entry.getKey();
    			}
    		}else {
    			if(entry.getValue().getSuit().equals(Triunfo.getSuit())) {
    				jugadorGanador=entry.getKey();
    			}
    		}
    		
    		//System.out.println("Jugador Ganador: " + currentPlay.get(jugadorGanador).getNumber(). + " Value : " + entry.getValue());
    	}
        return jugadorGanador;
    }







//Esto está en Numbers
private static ArrayList<Numbers> indices = new ArrayList<Numbers>(
			Arrays.asList(Numbers.TWO, Numbers.FOUR, Numbers.FIVE, Numbers.SIX, Numbers.SEVEN, Numbers.JACK,
					Numbers.HORSE, Numbers.KING, Numbers.THREE, Numbers.ACE));

	@Override
	public int compare(Numbers arg0, Numbers arg1) {
		int result = indices.indexOf(arg0)-indices.indexOf(arg1);
		if(result<0) {
			return 1;
		}else if (result==0) {
			return 0;
		}else {
			return -1;
		}
	}
