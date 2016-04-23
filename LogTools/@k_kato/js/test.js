const profile = {timeline: [
    {year: 2010, lang: "Java"},
    {year: 2011, lang: "C#"  },
    {year: 2012, lang: "C#"  },
    {year: 2013, lang: "Java"},
    {year: 2014, lang: "C#"  },
    {year: 2015, lang: "Java"}
]};

const addProfile2016 = (p) => {
    p.timeline.push({
        year: 2016,
        lang: "🍣",
        do: "I'm writing a book about modern web development with C#",
        try: "Microsoft MVP"
    });
}

const showProfile = (p) => {
    console.log(p);
}

addProfile2016(profile);
showProfile(profile);
