object Logic {

    def matchLikelihood(kitten: Kitten, buyer: BuyerPreferences): Double = {
        val matches = buyer.attributes.map((x) => kitten.attributes.contains(x))
        val nums = matches.map(x => if (x) 1.0 else 0.0)
        nums.sum / matches.length
    }
}