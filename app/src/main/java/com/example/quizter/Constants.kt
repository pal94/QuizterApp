package com.example.quizter

object Constants{

    const val TOTAL_QUESTIONS:String = "total_question"
    const val CORRECT_ANSWER: String="correct_answers"

    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val ques1 = Question(1, "What country does this flag belong to?",
              R.drawable.germany,
                "Sweden",
                "Germany",
                "Italy",
                "Bahamas",
                2,
        )
        val ques2 = Question(1, "What country does this flag belong to?",
                R.drawable.india,
                "India",
                "US",
                "Italy",
                "Canada",
                1,
        )
        val ques3 = Question(1, "What country does this flag belong to?",
                R.drawable.france,
                "Switzerland",
                "UK",
                "France",
                "Thailand",
                3,
        )
        val ques4 = Question(1, "What country does this flag belong to?",
                R.drawable.us,
                "US",
                "Australia",
                "New Zealand",
                "UK",
                1,
        )
        val ques5 = Question(1, "What country does this flag belong to?",
                R.drawable.uk,
                "Sweden",
                "UK",
                "Australia",
                "None",
                2,
        )
        val ques6 = Question(1, "What country does this flag belong to?",
                R.drawable.american_somana,
                "Trinidad and Tobago",
                "Syria",
                "Italy",
                "American Somana",
                4,
        )

        questionsList.add(ques1)
        questionsList.add(ques2)
        questionsList.add(ques3)
        questionsList.add(ques4)
        questionsList.add(ques5)
        questionsList.add(ques6)

        return questionsList
    }
}