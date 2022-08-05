
export function loadView(view) {
    return () => import(`@/views/${view}.vue`)
}

export default [
  {
    path: '/',
    name: 'home',
    title: 'Home',
    component: loadView('Home'),
  },
  {
    path: '/provenance/doc/:id',
    name: 'document',
    title: 'Document View',
    component: loadView('provenance/Document'),
  },
  {
    path: '/claims',
    name: 'claims',
    title: 'Claim Frames List',
    component: loadView('claims/ClaimFramesList'),
  },
  {
    path: '/claims/topic/:claimid',
    name: 'claimDetails',
    title: 'Claim Frame Detail',
    component: loadView('claims/ClaimFrameDetails'),
  },
  {
    path: '/settings/graphs',
    name: 'settings',
    title: 'Manage TripleStore Graphs',
    component: loadView('settings/GraphLoad'),
  },
  {
    path: '/help/guide',
    name: 'help',
    title: 'Help Guide',
    component: loadView('help/Guide'),
  },
  //TODO: Do we need this?
  {
    path: '/test',
    name: 'test',
    title: 'Test',
    component: loadView('StartTA3'),
  },
  {
    // redirect all other unknowns to a 404 not found page
    path: '*',
    name: '404',
    title: '404',
    component: loadView('NotFound'),
  },
];
