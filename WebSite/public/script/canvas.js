const canvas = document.getElementById('renderSurface');
let myFluid = new Fluid(canvas);

myFluid.mapBehaviors({
    sim_resolution: 128,
    dye_resolution: 512,

    paused: false,
    embedded_dither: true,

    dissipation: .97,
    velocity: .98,
    pressure: .8,
    pressure_iteration: 20,
    curl: 0,
    emitter_size: 0.5,

    render_shaders: true,
    multi_color: true,

    render_bloom: false,
    bloom_iterations: 8,
    bloom_resolution: 256,
    intensity: 0.8,
    threshold: 0.6,
    soft_knee: 0.7,
    transparent: true
})

myFluid.activate();