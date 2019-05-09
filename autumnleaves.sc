(
~rev2 = ScProphetRev2();
~rev2.connect;
)

(
s.waitForBoot({
    var barcheck = {
        | bar, expectedlength, baridx |
        // expected length is expressed in #whole notes
        var p = Panola.new(bar);
        var dur = p.totalDuration;
        if (dur != expectedlength) {
            ("Bar check at bar " ++ baridx ++ "(" ++ bar ++ ") failed. Length is " ++ dur ++ " expected length is " ++ expectedlength).postln;
        };
    };
    var melody_per_meas = ["r_4@tempo[120] r_8 g4 a_4 b-",
        "e-5_1",
        "r_4. f4_8 r g r a",
        "d5_1",
        "r_4 r_8 e-4 g b- d5_4",
        "c_2 r",
        "r_8 d4 r d e d f#_4",
        "b-4_1",
        "r_8 g4 r g a_4 b-_4",
        "e-5_1",
        "r_8 <c4 e-4> f4 g a b- c5 r",
        "d5_1",
        "r_2 r_8 e-4_8 g_8*2/3 b- d5",
        "c5_8 c r_4 r_4 r_4",
        "r_8 f#4_8 a c5 e- f e- c",
        "c#5_8 d b-4 g c5 a4 f#_4",
        "b-4_8 f# g_4 a g",
        "a4_2 <c_4. d> a_8",
        "a4_2 g_4 a_8 b-",
        "b-4_1",
        "r_2 r_4 a_8 b-_8",
        "c5_2. f4_4",
        "f5_2 <a4 d#5>",
        "<f4_2 b- d5> r2",
        "r_2 r_4 d5_8*2/3 c d",
        "e-5_2. c_4",
        "a4_2. e-5_4",
        "<b-4_2 d5> <a4 c#5>",
        "<a-4_4 c5> r_8 <d4_8*5 b->",
        "<g4_2 c5> r_8 <f4_4 b-> <e_8 f# a>",
        "<e_2 f# a> b-_4 d",
        "g_1",
        "r_1"
    ];

    var skip_measures = 0;
    var melody_joined = melody_per_meas[skip_measures..].join(" ");
    var melody_panola = Panola.new(melody_joined);
    var melody_pattern = melody_panola.asPbind();

    var accompaniment_per_meas = [
        "r_1@tempo[120]",
        "r_2 <c3_4. b-3 e-4 g4> <f3_8 a3 e-4 g-4 a4>",
        "<f3_4. a3 e-4 g-4 a4> r_8 r_2",
        "r_2 <b-2_4. g3 c4 f a> <e-3_8 b-3 d4 g>",
        "<e-3_2 b-3 d4 g> r_2",
        "r_2 <a2_4. g3 c4 e- g> <d3_8 f# c4 f>",
        "<d3_4 f# c4 f> r_4 r_2",
        "r_8 <g2_8 f3 b- d4> r_4 <g2_4. e3 b- d4> <g2_8 d3 b- d4>",
        "<g2_2 d3 b- d4> r_2",
        "r_2 <c3_4. b-3 d4 e- g> <f3_8 a d4 e4- g- a>",
        "<f3_4 a d4> r_4 r_2",
        "r_2 <b-2_8 a-3 d-4 g- b-> <g3_8 c4 f a> r_8 <e-2 b- f3 b3- d4 g>",
        "<e-2_4. b- f3 b3- d4 g> e-_8*5",
        "r_2 <a2_4. g3 c4 e- g> <d3_8 f# c4 f>",
        "<d3_4 f# c4 f> r_4 r_8 <d3_8 f# a> r_4",
        "r_8 <g2_8 d3 b-> r_4 <a2_8 g3> r_8 r_4",
        "<g2_4. f3> <f3_8 a b- d4> r_4 r_8 e-3_8",
        "a2_2 g3_4 d3_4",
        "d3_8 <f#3_4 c4> d2_8 r_2",
        "r_8 <a3_8 b-3 d4> r_8 <f#3_4. b- d4> a-2_4",
        "<a-2_4. g-3 b- c4 f> <g2_4. f3 a b- d4> r_4",
        "c3_2 <b-3_2 e-4>",
        "f2_2 <b2_2 a3>",
        "<b-2_2 g3> <e2 d3 g# d4 f#>",
        "<e-2_2 b- g3 b- d4 f> b-2_4 r_4",
        "a2_4. <g3_8*5 c4>",
        "d3_4. <f#_8*5 c4 f>",
        "<a3_2 b- d4> <f#3 a c#4>",
        "<f3_4 a- c4> r_8 <b-2_8*3 a-3> e-2_4",
        "<e-3_2 b- c4> r_4 r_8 <d3_8 f# c4>",
        "<d3_2 f# c4> r_4 r_4",
        "r_8 <g2_8 f3 b- d4> r_4 <g2_4. e3 b- d4> <g2_8 d3 b- d4>",
        "<g2_2 d3 b- d4> r_2"
    ];
    var accompaniment_joined = accompaniment_per_meas[skip_measures..].join(" ");
    var accompaniment_panola = Panola.new(accompaniment_joined);
    var accompaniment_pattern = accompaniment_panola.asPbind();

    var combined_pattern1 = Ppar([melody_pattern, accompaniment_pattern], 1);

    var melody_notes = melody_panola.midinotePattern.asStream.all;
    var melody_durations = melody_panola.durationPattern.asStream.all;
    var var1_melody_notes = [];
    var var1_melody_durs = [];
    var accompaniment_notes = accompaniment_panola.midinotePattern.asStream.all;
    var accompaniment_durations = accompaniment_panola.durationPattern.asStream.all;
    var var1_accompaniment_notes = [];
    var var1_accompaniment_durs = [];
    var variation1_pattern;
    var variation2_pattern;
    var variation3_pattern;
    var all_variations;

    var expand_note = {
        | note, duration, range=6, maxdiv=3, modmult=5 |
        var resulting_notes = [];
        var resulting_durs = [];
        var divisions = 1.rrand(maxdiv);
        if (note.class == Rest) {
            resulting_notes = resulting_notes.add(note);
            resulting_durs = resulting_durs.add(duration);
        } /* else */ {
            if (divisions > 2) {
                resulting_notes = resulting_notes.add(note);
                resulting_durs = resulting_durs.add(duration/divisions);
                (divisions-2).do({
                    var mod = range.rrand(range.neg);
                    resulting_notes = resulting_notes.add(note+(mod*modmult));
                    resulting_durs = resulting_durs.add(duration/divisions);
                });
                resulting_notes = resulting_notes.add(note);
                resulting_durs = resulting_durs.add(duration/divisions);
            } {
                resulting_notes = resulting_notes.add(note);
                resulting_durs = resulting_durs.add(duration);
            };
        };

        [resulting_notes, resulting_durs]
    };

    var chordify = {
        | notes, durations, bylength=1 /*bylength = length in whole notes */|
        var measures_note = [];
        var measure_note = [];
        var measures_dur = [];
        var measure_dur = [];
        var accumulated_length = 0;
        notes.do({
            | note, idx |

            var duration = durations[idx];
            measure_note = measure_note.add(note);
            measure_dur = measure_dur.add(duration);
            accumulated_length = accumulated_length + duration;

            if ((accumulated_length >= bylength) || (idx == notes.size) ) {
                measures_note = measures_note.add(measure_note.flatten);
                measures_dur = measures_dur.add(measure_dur.sum);
                measure_note = [];
                measure_dur = [];
                accumulated_length = 0;
            };
        });
        [measures_note, measures_dur];
    };

    melody_panola.midinotePattern.asStream.all.do({
        | note, idx |
        if (note.class == Array) {
            var length = note.size;
            length.do({
                |i|
                var decorated = expand_note.(note[i], melody_durations[idx]/length, 4, 6, 1);
                var1_melody_notes = var1_melody_notes ++ decorated[0];
                var1_melody_durs = var1_melody_durs ++ decorated[1];
            });
        } /*else*/ {
            var decorated = expand_note.(note, melody_durations[idx], 4, 6, 1);
            var1_melody_notes = var1_melody_notes ++ decorated[0];
            var1_melody_durs = var1_melody_durs ++ decorated[1];
        };
    });

    accompaniment_panola.midinotePattern.asStream.all.do({
        | note, idx |
        if (note.class == Array) {
            var length = note.size;
            length.do({
                |i|
                var decorated = expand_note.(note[i], accompaniment_durations[idx]/length, 3, 3, 1);
                var1_accompaniment_notes = var1_accompaniment_notes ++ decorated[0];
                var1_accompaniment_durs = var1_accompaniment_durs ++ decorated[1];
            });
        } /*else*/ {
            var decorated = expand_note.(note, accompaniment_durations[idx], 3, 3, 1);
            var1_accompaniment_notes = var1_accompaniment_notes ++ decorated[0];
            var1_accompaniment_durs = var1_accompaniment_durs ++ decorated[1];
        };
    });

    // F1 P22 (PLUCKY/WASHY)
    // F1 P48 (WOOD ORGAN)/F3 P87 (ORGAN/CLEARER)
    // F1 P69 (SOLO XTREME)/F2 P82 (SOLO XTREME SOFTER)
    // F1 P79 (modwheel,CRYSTALS)
    // F3 P66 (SWING)
    // F3 P71 (CAPRICIOUS)
    // F3 P100 (SPACY/WASHY)
    // F3 P103 (SPACY)
    // F3 112 (SAD)
    // F3 120 (XTREME CLEAR)
    // F2 52 (XTREME NOT CLEAR)
    // F2 76 (XTREME MOSTLY BASS)
    variation1_pattern = Ppar([
        Pbind(
            \instrument, \default,

            // \type, \midi,
            // \midicmd, \noteOn,
            // \midiout, ~rev2.midi_out, // must provide the MIDI target here
            // \chan, 0,

            \midinote, Pseq(var1_melody_notes),
            \dur, Pseq(var1_melody_durs)),
        Pbind(
            \instrument, \default,

            // \type, \midi,
            // \midicmd, \noteOn,
            // \midiout, ~rev2.midi_out, // must provide the MIDI target here
            // \chan, 0,

            \midinote, Pseq(var1_accompaniment_notes),
            \dur, Pseq(var1_accompaniment_durs))
        ], inf);

    variation2_pattern = Ppar([
        Pbind(
            \instrument, \default,
            \midinote, Pseq(melody_panola.midinotePattern.asStream.all.reverse),
            \dur, Pseq(melody_panola.durationPattern.asStream.all),
        ),
/*        Pbind(
            \instrument, \default,
            \midinote, Pseq(chordify.(melody_panola.midinotePattern.asStream.all,melody_panola.durationPattern.asStream.all,1)[0]),
            \dur, Pseq(chordify.(melody_panola.midinotePattern.asStream.all,melody_panola.durationPattern.asStream.all,1)[1]),
        ),
        */
        Pbind(
            \instrument, \default,

            // \type, \midi,
            // \midicmd, \noteOn,
            // \midiout, ~rev2.midi_out, // must provide the MIDI target here
            // \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[0]),
            \dur, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[1]),
        ),
    ]);

    variation3_pattern = Ppar([
        Pbind(
            \instrument, \default,

            // \type, \midi,
            // \midicmd, \noteOn,
            // \midiout, ~rev2.midi_out, // must provide the MIDI target here
            // \chan, 0,

            \midinote, Pseq(melody_panola.midinotePattern.asStream.all.reverse),
            \dur, Pseq(melody_panola.durationPattern.asStream.all),
        ),
        Pbind(
            \instrument, \default,

            // \type, \midi,
            // \midicmd, \noteOn,
            // \midiout, ~rev2.midi_out, // must provide the MIDI target here
            // \chan, 0,

            \midinote, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[0].dup(3).flatten),
            \dur, Pseq(chordify.(accompaniment_panola.midinotePattern.asStream.all,accompaniment_panola.durationPattern.asStream.all,1)[1].dup(3).flatten/3),
        ),
    ]);

    all_variations = Pseq([variation2_pattern, variation3_pattern, variation1_pattern]);
    all_variations.play;

    melody_per_meas.do({|bar,baridx|
        barcheck.(bar, 1, baridx);
    });
    accompaniment_per_meas.do({|bar,baridx|
        barcheck.(bar, 1, baridx);
    });

});
)
